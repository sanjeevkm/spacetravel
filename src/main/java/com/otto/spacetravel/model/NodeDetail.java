package com.otto.spacetravel.model;

/*
 * Enum defined for each node/stop/stas systems.
 * Each enum defines node id and name. 
 */
public enum NodeDetail {
	SOLAR_SYSTEM(0, "Solar System"), BETELGEUSE(1, "Betelgeuse"),
	ALPHA_CENTAURI(2, "Alpha Centauri"), VEGA(3, "Vega"), SIRIUS(4, "Sirius");

	private Integer id;
	private String name;

	private NodeDetail(Integer id, String name){
		this.id = id;
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public static NodeDetail getNodeDetailById(Integer id) {
		for (NodeDetail type : NodeDetail.values()) {
			if (type.getId().equals(id)) {
				return type;
			}
		}

		//TODO: May create enum specific exception
		throw new IllegalArgumentException("Illegal NodeDetail id");
	}
}
