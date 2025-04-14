import { Injectable } from '@angular/core';
import Peer from 'peerjs';
import { environment } from 'src/environment/environment';  // Make sure to set the correct WebSocket URL in environment.ts

@Injectable({
  providedIn: 'root',
})
export class VideoCallService {
  peer!: Peer;
  myStream!: MediaStream;
  socket!: WebSocket;
  myPeerId: string = '';
  private chatHandler?: (msg: any) => void;

  constructor() {}

  initPeer(userId?: string): Peer {
    // Initialize the PeerJS peer object with or without userId
    this.peer = userId
      ? new Peer(userId, this.getPeerOptions())  // Use provided userId if any
      : new Peer(this.getPeerOptions()); // Use random peer ID if no userId is provided

    this.peer.on('open', (id) => {
      this.myPeerId = id; // Store the generated Peer ID
      console.log('✅ My Peer ID:', id);
      this.sendSignal({ type: 'register', from: this.myPeerId }); // Send the signal to register
    });

    return this.peer;
  }

  private getPeerOptions() {
    return {
      host: 'localhost',
      port: 9000,  // Your PeerJS server port
      path: '/peerjs',
      secure: false,  // Set true if you're using an SSL-secured server
    };
  }

  async getUserMedia(): Promise<MediaStream> {
    try {
      this.myStream = await navigator.mediaDevices.getUserMedia({
        video: true,
        audio: true,
      });
      return this.myStream;  // Return media stream (camera and microphone)
    } catch (error) {
      console.error('Error accessing media devices:', error);
      throw error;
    }
  }

  connectWebSocket(onChatMessage?: (msg: any) => void): void {
    this.chatHandler = onChatMessage;  // Set up handler for incoming chat messages
    this.socket = new WebSocket(environment.wsUrl);  // Establish WebSocket connection

    this.socket.onopen = () => {
      console.log('✅ WebSocket connected');
      if (this.myPeerId) {
        this.sendSignal({ type: 'register', from: this.myPeerId });  // Send register signal after WebSocket is connected
      }
    };

    // Handle incoming messages (e.g., chat messages)
    this.socket.onmessage = (message) => {
      const data = JSON.parse(message.data);  // Parse incoming message
      console.log('📥 Message from WS:', data);

      if (data.type === 'chat' && this.chatHandler) {
        this.chatHandler(data);  // Pass the chat message to the handler
      }
    };

    // Log WebSocket errors
    this.socket.onerror = (error) => {
      console.error('WebSocket error:', error);
    };
  }

  sendChatMessage(to: string, content: string) {
    const msg = {
      type: 'chat',
      from: this.myPeerId,  // Sender's Peer ID
      to,  // Receiver's Peer ID
      content,  // Message content
    };

    if (this.socket.readyState === WebSocket.OPEN) {
      this.socket.send(JSON.stringify(msg));  // Send the chat message over WebSocket
    } else {
      console.warn('❌ WebSocket not open');
    }
  }

  // Initiate a call to another peer
  callUser(peerId: string, stream: MediaStream) {
    if (!this.peer) {
      console.error('❌ PeerJS not initialized');
      return;
    }
    return this.peer.call(peerId, stream);  // Call another peer using PeerJS
  }

  // Handle incoming call from another peer
  handleIncomingCalls(setRemoteStream: (stream: MediaStream) => void) {
    this.peer.on('call', (call) => {
      if (this.myStream) {
        call.answer(this.myStream);  // Answer the incoming call with the local stream
      }

      // On stream reception, set the remote video stream
      call.on('stream', (remoteStream) => {
        setRemoteStream(remoteStream);
      });

      call.on('error', (err) => {
        console.error('Call error:', err);
      });
    });
  }

  sendSignal(signal: any) {
    // Send a signal (like a register or chat signal) over WebSocket
    if (this.socket && this.socket.readyState === WebSocket.OPEN) {
      this.socket.send(JSON.stringify(signal));
    }
  }
}
