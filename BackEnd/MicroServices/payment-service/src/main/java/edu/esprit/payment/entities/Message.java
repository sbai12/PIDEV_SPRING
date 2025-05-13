package edu.esprit.payment.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public  class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String role;
    @Column(length = 5000)
    @Lob
    private String content;

    public Message(String role, String content) {
        this.role = role;
        this.content = content;
    }
}
