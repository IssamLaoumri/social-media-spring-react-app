package com.laoumri.springbootbackend.enums;

public enum EMessage {
    // Success messages
    FRIEND_REQUEST_SENT,
    FRIEND_REQUEST_CANCELED,
    FRIEND_REQUEST_ACCEPTED,
    FRIEND_REQUEST_DELETED,
    UNFRIEND_SUCCESSFUL,

    // Error messages
    ALREADY_FRIENDS,
    FRIEND_REQUEST_ALREADY_SENT,
    FRIEND_REQUEST_NOT_FOUND,
    NOT_FRIENDS,
    REQUEST_NOT_ALLOWED,
    USER_NOT_FOUND,
}
