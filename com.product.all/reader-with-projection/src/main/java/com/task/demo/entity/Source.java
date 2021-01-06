package com.task.demo.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static javax.persistence.GenerationType.IDENTITY;

@NoArgsConstructor
@Setter
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

@Entity
@Table(name = "source")
public class Source implements Serializable {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@EqualsAndHashCode.Include
	private Integer id;

	@Column(name = "name")
	private String name;

	@Column(name = "logo")
	private String logo;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "source")
	private Set<Product> products;


	public Source(int id) {
		this.id = id;
	}
}
