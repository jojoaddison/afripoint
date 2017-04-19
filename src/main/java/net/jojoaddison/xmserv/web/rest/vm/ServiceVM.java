package net.jojoaddison.xmserv.web.rest.vm;

import net.jojoaddison.xmserv.domain.Afripoint;

public class ServiceVM {
	String id;
	String icon;
	String name;
	String description;
	String contact;

	public ServiceVM(){}

	public ServiceVM(Afripoint service){
		setId(service.getId());
		setName(service.getName());
		setDescription(service.getDescription());
		setIcon(service.getIcon());
		setContact(service.getContact());
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public ServiceVM contact(String contact){
		this.contact = contact;
		return this;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public ServiceVM icon(String icon){
		this.icon = icon;
		return this;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public ServiceVM name(String name){
		this.name = name;
		return this;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ServiceVM description(String description){
		this.description = description;
		return this;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
