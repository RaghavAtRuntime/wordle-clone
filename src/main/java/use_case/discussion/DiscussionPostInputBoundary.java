package use_case.discussion;

public interface DiscussionPostInputBoundary {
    void addPost(DiscussionPostInputData inputData);
    void getAllPosts();
}