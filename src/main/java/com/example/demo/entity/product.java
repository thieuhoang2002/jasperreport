package com.example.demo.entity;
import jakarta.persistence.*;


@Entity
@Table(name = "products") //tên bảng trong database
public class product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long productId;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private int quantity;

	public product() {}
	
	public product(Long productId, String name, int quantity) {
		super();
		this.productId = productId;
		this.name = name;
		this.quantity = quantity;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	
}




	
