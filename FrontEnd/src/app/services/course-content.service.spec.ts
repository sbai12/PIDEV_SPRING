import { TestBed } from '@angular/core/testing';


describe('CourseContentService', () => {
  let service: CourseContentService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CourseContentService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
