package com.vpmsbcm.config;

import java.util.Scanner;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vpmsbcm.service.Service;

@Component
public class Config {

	@Autowired
	private Service service;

	private String id;

	public Config() {
	}

	@PostConstruct
	public void init() {
		System.out.println("Insert id for order client:");
		Scanner sc = new Scanner(System.in);
		id = sc.nextLine();
		service.init(id);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
