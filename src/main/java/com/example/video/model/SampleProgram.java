package com.example.video.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonBackReference;


@Entity
@Table(name = "sample_programs", uniqueConstraints = {
		@UniqueConstraint(columnNames = "name", name = "unique_sample_program_name") })

public class SampleProgram{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	int id;
	
	@Column(name = "name", nullable = false, length = 50)
	String name;
	
	@Column(name = "file", nullable = false, length = 100)
	String file;
	
	@Column(name = "description", length = 200)
	String description;

	
	@ManyToOne
	@JoinColumn(name = "video_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_sample_programs_video_id"))
	@JsonBackReference
	private Video video;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Video getVideo() {
		return video;
	}

	public void setVideo(Video video) {
		this.video = video;
	}

	public SampleProgram() {
		super();
	}

}
