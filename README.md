# Spring Boot Commentable Starter

A Spring Boot library for adding comment functionality to entities, inspired by Ruby's acts_as_commentable.

## Installation

Add the dependency to your `pom.xml`:

```xml
<dependency>
    <groupId>com.coupa.commentable</groupId>
    <artifactId>spring-boot-commentable</artifactId>
    <version>1.0.0</version>
</dependency>
```

## Usage

Enable the module in your Spring Boot application:

```java
import com.coupa.commentable.annotation.EnableCommentable;

@SpringBootApplication
@EnableCommentable
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```

Implement the `Commentable` or `AssignmentCommentable` interface in your entity:

```java
import com.coupa.commentable.interfaces.AssignmentCommentable;
import com.coupa.commentable.model.Comment;

@Entity
public class Invoice implements AssignmentCommentable {
    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(mappedBy = "commentable")
    private List<Comment> comments = new ArrayList<>();

    @Override
    public Long getId() { return id; }

    @Override
    public String getCommentableType() { return "Invoice"; }

    @Override
    public List<Comment> getComments() { return comments; }

    @Override
    public void addComment(Comment comment) {
        comments.add(comment);
        // Set inverse relationship if needed
    }

    @Override
    public List<Comment> getUnreadComments(Long userId) {
        return comments.stream()
            .filter(c -> c.getReadReceipts().stream().noneMatch(r -> r.getUserId().equals(userId)))
            .toList();
    }

    @Override
    public boolean supportsComments() { return true; }
    @Override
    public boolean supportsMentions() { return true; }
    @Override
    public boolean supportsAttachments() { return true; }
    @Override
    public boolean allowMentioneeAccess() { return true; }

    @Override
    public void beforeAssign(Object assignee, AssignmentOptions options) {
        // Custom assignment logic
    }

    @Override
    public void addCommentForAssignment(Comment comment, AssignmentOptions options) {
        addComment(comment);
    }

    @Override
    public boolean supportsAssignmentComments() { return true; }
}
```

Use `CommentService` to manage comments:

```java
@Autowired
private CommentService commentService;

public void example() {
    Comment comment = commentService.createComment(1L, "Invoice", "Hello @user123", 1L, false);
    commentService.addAttachment(comment.getId(), "file.txt", "/path/file.txt", 100L, "text/plain");
    commentService.markAsRead(comment.getId(), 1L);
    List<Comment> comments = commentService.getComments(1L, "Invoice");
    List<Comment> unread = commentService.getUnreadComments(1L, "Invoice", 1L);
}
```

## Features
- Add comments to entities with @mentions and attachments.
- Track read/unread status per user.
- Support assignment-related comments.
- Auto-configured with minimal setup using `@EnableCommentable`.
- Uses an adapter pattern for file storage (integrate with your storage solution).

## Configuration
Minimal `application.properties` for testing with H2:

```properties
spring.datasource.url=jdbc:h2:mem:testdb
spring.jpa.hibernate.ddl-auto=update
```

## Performance Considerations

- Use `FetchType.LAZY` to avoid unnecessary data loading.
- For complex queries, consider `JOIN FETCH` to prevent N+1 issues (e.g., fetching comments with attachments in one query).
- Example:
  ```java
  @Query("SELECT c FROM Comment c LEFT JOIN FETCH c.attachments WHERE c.commentableId = :id AND c.commentableType = :type")
  List<Comment> findByCommentableIdAndCommentableTypeWithAttachments(@Param("id") Long id, @Param("type") String type);
  ```

## Building and Using the Library

1. Build the JAR:
   ```bash
   mvn package
   ```
2. Install locally or deploy to a Maven repository:
   ```bash
   mvn install
   ```
3. Include in another project’s `pom.xml` as shown in Installation.

## Notes
- Notification and access control are placeholders; customize based on your application’s infrastructure.
- Ensure your storage module handles file uploads for attachments. 