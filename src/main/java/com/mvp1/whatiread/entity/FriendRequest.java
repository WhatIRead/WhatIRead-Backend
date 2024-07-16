package com.mvp1.whatiread.entity;

import com.mvp1.whatiread.entity.user.User;
import com.mvp1.whatiread.utils.RequestStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.io.Serial;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(
    uniqueConstraints = @UniqueConstraint(columnNames = {"USER_ID", "FRIEND_ID"})
)
public class FriendRequest implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @ManyToOne
  private User user;
  @ManyToOne
  private User friend;
  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private RequestStatus status;
  private String requestMessage;
}

