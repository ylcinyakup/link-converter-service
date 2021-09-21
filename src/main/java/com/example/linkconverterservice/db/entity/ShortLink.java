package com.example.linkconverterservice.db.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.io.Serializable;

@SuperBuilder
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "short_links", uniqueConstraints = {
        @UniqueConstraint(name = "UK_short_links_link", columnNames = {"link"}),
        @UniqueConstraint(name = "UK_short_links_shortLink", columnNames = {"short_link"})
})
public class ShortLink extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1155292669948577086L;

    @Column(name = "link")
    private String link;

    @Column(name = "short_link")
    private String shortLink;

}
