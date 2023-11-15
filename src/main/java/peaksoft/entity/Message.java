package peaksoft.entity;

import jakarta.persistence.*;


import java.util.Date;

@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String content;

    private String sender;

    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;
}