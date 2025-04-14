import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { VideoCallService } from 'src/app/services/video-call.service';

@Component({
  selector: 'app-video-call',
  templateUrl: './video-call.component.html',
  styleUrls: ['./video-call.component.css'],
})
export class VideoCallComponent implements OnInit {
  @ViewChild('myVideo') myVideo!: ElementRef;
  @ViewChild('peerVideo') peerVideo!: ElementRef;
  @ViewChild('chatMessages') chatMessages!: ElementRef;  // Reference to chat messages container

  peerId: string = '';
  myPeerId: string = '';
  myStream!: MediaStream;
  currentCall: any;

  messages: string[] = [];
  chatInput: string = '';

  isMuted: boolean = false; // Flag to keep track of mute/unmute state

  constructor(private videoCallService: VideoCallService) {}

  async ngOnInit() {
    const peer = this.videoCallService.initPeer();

    peer.on('open', (id) => {
      this.myPeerId = id;
      console.log('Generated Peer ID:', id);
    });

    // Connect to WebSocket and handle chat messages
    this.videoCallService.connectWebSocket((chatMsg) => {
      if (chatMsg && chatMsg.type === 'chat') {
        this.messages.push(`${chatMsg.from}: ${chatMsg.content}`);
        setTimeout(() => this.scrollToBottom(), 100); // Scroll to the latest message
      }
    });

    this.myStream = await this.videoCallService.getUserMedia();
    this.myVideo.nativeElement.srcObject = this.myStream;

    this.videoCallService.handleIncomingCalls((remoteStream) => {
      this.peerVideo.nativeElement.srcObject = remoteStream;
    });
  }

  // Call another peer
  callUser() {
    if (this.peerId) {
      const call = this.videoCallService.callUser(this.peerId, this.myStream);
      this.currentCall = call;

      call?.on('stream', (peerStream) => {
        this.peerVideo.nativeElement.srcObject = peerStream;
      });
    } else {
      alert('Please enter a Peer ID to call.');
    }
  }

  // Share the screen
  async shareScreen() {
    try {
      const screenStream = await navigator.mediaDevices.getDisplayMedia({ video: true });
      this.myVideo.nativeElement.srcObject = screenStream;

      if (this.currentCall) {
        const sender = this.currentCall.peerConnection
          .getSenders()
          .find((s: RTCRtpSender) => s.track && s.track.kind === 'video');

        if (sender) {
          sender.replaceTrack(screenStream.getVideoTracks()[0]);
        }
      }

      screenStream.getVideoTracks()[0].onended = async () => {
        this.myStream = await this.videoCallService.getUserMedia();
        this.myVideo.nativeElement.srcObject = this.myStream;

        if (this.currentCall) {
          const sender = this.currentCall.peerConnection
            .getSenders()
            .find((s: RTCRtpSender) => s.track && s.track.kind === 'video');

          if (sender) {
            sender.replaceTrack(this.myStream.getVideoTracks()[0]);
          }
        }
      };
    } catch (error) {
      console.error('Error sharing screen:', error);
    }
  }

  // Send chat message
  sendChat() {
    if (this.chatInput.trim() && this.peerId) {
      this.videoCallService.sendChatMessage(this.peerId, this.chatInput.trim());
      this.messages.push(`Me: ${this.chatInput.trim()}`);
      this.chatInput = '';
      setTimeout(() => this.scrollToBottom(), 100);
    }
  }

  // Scroll to the bottom of the chat container
  scrollToBottom() {
    if (this.chatMessages) {
      this.chatMessages.nativeElement.scrollTop = this.chatMessages.nativeElement.scrollHeight;
    }
  }

  // Function to mute/unmute the local stream's audio
  toggleMute() {
    if (this.myStream) {
      const audioTrack = this.myStream.getAudioTracks()[0];
      if (audioTrack) {
        audioTrack.enabled = !audioTrack.enabled;  // Toggle the 'enabled' property to mute/unmute
        this.isMuted = !audioTrack.enabled;  // Update the mute state
      }
    }
  }
}
