package com.community.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Set;


@Embeddable
public enum Role {
    USER, ADMIN
}