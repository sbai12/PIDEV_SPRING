export class Post {
  id: number;
  name: string;
  content: string;
  postedBy: string;
  img: string;
  date: Date;
  likeCount: number;
  viewCount: number;
  tags: string[];

  constructor(id: number, name: string, content: string, postedBy: string, img: string, date: Date, likeCount: number, viewCount: number, tags: string[] = []) {
    this.id = id;
    this.name = name;
    this.content = content;
    this.postedBy = postedBy;
    this.img = img;
    this.date = date;
    this.likeCount = likeCount;
    this.viewCount = viewCount;
    this.tags = [];  // Initialize tags as an empty array
  }
}
