package Main.Admin.IngredientsManager.Model;

import Main.Admin.DataManager.Controller.AdminProductController;
import Main.Admin.DataManager.Model.ProductInTable;
import Main.Entity.DataAccess.DAO;
import Main.Entity.Element.Ingredient;
import Main.Entity.Element.Product;
import Main.Entity.Element.ProductRecipe;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RecipesModel {
    private ObservableList<Product> productList;
    private ObservableList<Ingredient> ingredientList;
    private ObservableList<String> IngredientNameList;
    private ObservableList<String> IngredientIdList;
    private ObservableList<String> ProductNameList;
    private ObservableList<String> ProductIdList;

    private ObservableList<ProductRecipe> recipesList;

    private DAO dao;

    public RecipesModel() {
        this.init();
    }

    public void init() {
        this.dao = new DAO();
        ingredientList = IngredientApplicationModel.ingredientList;
        productList = FXCollections.observableArrayList(this.dao.getAllProduct());
        recipesList = FXCollections.observableArrayList();

        setIngredientNameList();
        setIngredientIdList();

        setProductNameList();
        setProductIdList();

        this.productList.addListener((ListChangeListener<Product>) change -> {
            ProductNameList.clear();
            ProductIdList.clear();
            for (Product product : productList) {
                ProductNameList.add(product.getProductName());
                ProductIdList.add(product.getProductId());
            }
        });

        this.ingredientList.addListener((ListChangeListener<Ingredient>) change -> {
            IngredientNameList.clear();
            IngredientIdList.clear();
            for (Ingredient ingredient : ingredientList) {
                IngredientNameList.add(ingredient.getIngredientName());
                IngredientIdList.add(ingredient.getIngredientId());
            }
        });


    }

    public ObservableList<Product> getProductList() {
        return productList;
    }

    public void setProductList(ObservableList<Product> productList) {
        this.productList = productList;
    }

    public void setIngredientList(ObservableList<Ingredient> ingredientList) {
        this.ingredientList = ingredientList;
    }

    public ObservableList<ProductRecipe> getRecipesList() {
        return recipesList;
    }

    public void setRecipesList(ObservableList<ProductRecipe> recipesList) {
        this.recipesList = recipesList;
    }

    public DAO getDao() {
        return dao;
    }

    public void setDao(DAO dao) {
        this.dao = dao;
    }

    public ObservableList<Ingredient> getIngredientList() {
        return ingredientList;
    }

    public ObservableList<String> getIngredientNameList() {
        return IngredientNameList;
    }

    public void setIngredientNameList() {
        IngredientNameList = FXCollections.observableArrayList();
        this.getIngredientList().forEach(data -> {
            IngredientNameList.add(data.getIngredientName());
        });

    }

    public ObservableList<String> getIngredientIdList() {
        return IngredientIdList;
    }

    public void setIngredientIdList() {
        IngredientIdList = FXCollections.observableArrayList();
        this.getIngredientList().forEach(data -> {
            IngredientIdList.add(data.getIngredientId());
        });
    }

    public ObservableList<String> getProductNameList() {
        return ProductNameList;
    }

    public void setProductNameList() {
        ProductNameList = FXCollections.observableArrayList();
        this.getProductList().forEach(data -> {
            ProductNameList.add(data.getProductName());
        });

    }

    public ObservableList<String> getProductIdList() {
        return ProductIdList;
    }

    public void setProductIdList() {
        ProductIdList = FXCollections.observableArrayList();
        this.getProductList().forEach(data -> {
            ProductIdList.add(data.getProductId());
        });
    }

    public String findIngredientNameById(String id) {
        for (Ingredient ingredient : this.getIngredientList()) {
            if(id.equalsIgnoreCase(ingredient.getIngredientId())) {
                return ingredient.getIngredientName();
            }
        }
        return null;
    }

    public String findProductNameById(String id) {
        for (Product p : this.getProductList()) {
            if(id.equalsIgnoreCase(p.getProductId())) {
                return p.getProductName();
            }
        }
        return null;
    }

    public void addNewItem(ProductRecipe pr) {
//        this.getRecipesList().add(pr);
        String sql = String.format("Insert into ProductRecipes(productID, ingredientID, " +
                        "productQty, ingredientQty) values ('%s', '%s', '%d', '%d')", pr.getProductId(),
                pr.getIngredientId(), pr.getProductQty(), pr.getIngredientQty());
        dao.insert(sql);
        try {
            this.getRecipeOfChosenItem(pr.getProductId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateItem(int index, ProductRecipe pr) {
        this.getRecipesList().set(index, pr);
        String sql = String.format("Update ProductRecipes set productQty = '%d', ingredientQty = '%d' " +
                "where productID = '%s' and ingredientID = '%s'", pr.getProductQty(), pr.getIngredientQty(),
                pr.getProductId(), pr.getIngredientId());
        dao.insert(sql);
    }

    public void removeItem(ProductRecipe rp) {
        String sql = String.format("Delete from ProductRecipes where productID = '%s' and ingredientID = '%s'",
                rp.getProductId(), rp.getIngredientId());
        dao.insert(sql);
        this.getRecipesList().remove(rp);
    }

    public void getRecipeOfChosenItem(String productId) throws SQLException {
        this.recipesList.clear();
        ResultSet rs = dao.executeQuery("Select * from ProductRecipes where productID = '"+productId+"'");
        ProductRecipe pr;
        while(rs.next()) {
            pr = new ProductRecipe();
            pr.setProductId(rs.getString("productID"));
            pr.setIngredientId(rs.getString("ingredientID"));
            pr.setProductQty(rs.getInt("productQty"));
            pr.setIngredientQty(rs.getInt("ingredientQty"));
            this.recipesList.add(pr);
        }
    }

    public void saveResult(int qty) {
        String sql = String.format("Update ProductRecipes set productQty = '%d' " +
                "where productID = '%s'", qty, recipesList.get(0).getProductId());
        dao.insert(sql);
    }
}

