package com.samar.blog.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDto {
	
	private Integer categoryId;
	
	@NotEmpty
	@NotNull
	@Size(min=4,message = "category title sshould be greater than 4")
	private String categoryTitle;
	
	@NotEmpty
	@NotNull
	@Size(min=6,message = "category title should be greater than 6")
	private String categoryDescription;

}
