export interface Comment {
    id?: number;            // optional for similar reasons
    content: string;
    createdAt?: Date;
    postedBy: string;
    // Instead of embedding the full Post, you can store the associated postId:
    postId?: number;
  }
  
  