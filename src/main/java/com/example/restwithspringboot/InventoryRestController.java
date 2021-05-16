package com.example.restwithspringboot;

import java.sql.SQLException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

@RestController
public class InventoryRestController {

	dbController db = new dbController("sa", "");

	@RequestMapping(method = RequestMethod.GET, value = "/")
	public String index() {

		return "Welcome to the inventory RESTful API with spring boot!";
	}

	@RequestMapping(value = "/show-table", method = RequestMethod.GET)
	@ApiOperation(value = "List all the data for the client")
	public String showTableToTheClient() throws SQLException {
		String ans = db.showTable();
		System.out.println(ans);

		return ans;
	}

	@RequestMapping(value = "/read-product-details/{productId}", method = RequestMethod.GET)
	@ApiOperation(value = "Returns all of the inventory data", notes = "For a valid response, make sure that ID>=0")
	public String readDetails(@PathVariable("productId") String productId) {
		String ans = "";
		int product_id = Integer.parseInt(productId);
		ans = db.readProductDetails(product_id);

		return ans;
	}

	@RequestMapping(value = "/read-product-quantity/{productId}", method = RequestMethod.GET)
	@ApiOperation(value = "Returns a product details from the inventory by a given id")
	public String getQuantity(@PathVariable("productId") String productId) {
		String ans = "";
		int product_id = Integer.parseInt(productId);
		ans = db.readProductQuantity(product_id);

		return ans;
	}

	@RequestMapping(value = "/product/{productName}/{productAmount}/{InventoryCode}", method = RequestMethod.POST)
	@ApiOperation(value = "Adds a new product to the Inventory", notes = "The function recieves three parameters according to the database structure(id is auto increament primary key)")
	public String addProduct(@PathVariable("productName") String productName,
			@PathVariable("productAmount") String productAmount, @PathVariable("InventoryCode") String InventoryCode)
			throws SQLException {
		int inventory_code = Integer.parseInt(InventoryCode);
		int product_amount = Integer.parseInt(productAmount);

		return db.insertRecord(productName, product_amount, inventory_code);
	}

	@RequestMapping(value = "/set-quantity/{productId}/{productAmount}", method = RequestMethod.PUT)
	@ApiOperation(value = "Sets new quantity value for a specified product")
	public String setQuantity(@PathVariable("productId") String productId,
			@PathVariable("productAmount") String productAmount) throws SQLException {
		int product_id = Integer.parseInt(productId);

		return db.updateRecord(product_id, productAmount);
	}

	@RequestMapping(value = "/product/{productId}", method = RequestMethod.DELETE)
	@ApiOperation(value = "Deletes a product from the inventory by a specified id number")
	public String deleteProduct(@PathVariable("productId") String productId) throws SQLException {
		String ans = "";
		int product_id = Integer.parseInt(productId);
		ans = db.deleteProduct(product_id);

		return ans;
	}
}
