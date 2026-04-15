package com.csu.ecbackend.tzqServer.domain;

import lombok.Data;

@Data
public class Association {
	private String ownedEnd1;
	private String ownedEnd2;
	private String type1;
	private String type2;
}
