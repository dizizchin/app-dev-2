package com.rosal.serviceimpl;

import com.rosal.entity.ProductData;
import com.rosal.model.Product;
import com.rosal.model.ProductCategory;
import com.rosal.repostory.ProductDataRepository;
import com.rosal.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductDataRepository productDataRepository;


    public List<Product> getAllProducts() {
        List<ProductData>productDataRecords = new ArrayList<>();
        List<Product> products =  new ArrayList<>();

        productDataRepository.findAll().forEach(productDataRecords::add);
        Iterator<ProductData> it = productDataRecords.iterator();

        while(it.hasNext()) {
            Product product = new Product();
            ProductData productData = it.next();

            product.setId(productData.getId());
            product.setProductName(productData.getProductName());
            product.setCategoryName(productData.getCategoryName());
            product.setImageFile(productData.getImageFile());
            product.setPrice(productData.getPrice());
            product.setQuantity(productData.getQuantity());
            products.add(product);
        }
        return products;
    }
    @Override
    public List<ProductCategory> listProductCategories()
    {
        Map<String,List<Product>> mappedProduct = getCategoryMappedProducts();
        List<ProductCategory> productCategories = new ArrayList<>();
        for(String categoryName: mappedProduct.keySet()){
            ProductCategory productCategory =  new ProductCategory();
            productCategory.setCategoryName(categoryName);
            productCategory.setProducts(mappedProduct.get(categoryName));
            productCategories.add(productCategory);
        }
        return productCategories;
    }
    @Override
    public Map<String,List<Product>> getCategoryMappedProducts()
    {
        Map<String,List<Product>> mapProducts = new HashMap<String,List<Product>>();

        List<ProductData>productDataRecords = new ArrayList<>();
        List<Product> products;

        productDataRepository.findAll().forEach(productDataRecords::add);
        Iterator<ProductData> it = productDataRecords.iterator();

        while(it.hasNext()) {
            Product product = new Product();
            ProductData productData = it.next();

            if(mapProducts.containsKey(productData.getCategoryName())){
                products = mapProducts.get(productData.getCategoryName());
            }
            else {
                products = new ArrayList<Product>();
                mapProducts.put(productData.getCategoryName(), products);
            }
            product.setId(productData.getId());
            product.setProductName(productData.getProductName());
            product.setCategoryName(productData.getCategoryName());
            product.setImageFile(productData.getImageFile());
            product.setPrice(productData.getPrice());
            products.add(product);
        }
        return mapProducts;
    }

    @Override
    public Product[] getAll() {
            List<ProductData> productsData = new ArrayList<>();
            List<Product> products = new ArrayList<>();
            productDataRepository.findAll().forEach(productsData::add);
            Iterator<ProductData> it = productsData.iterator();
            while(it.hasNext()) {
                ProductData productData = it.next();
                Product product = new Product();
                product.setId(productData.getId());
                product.setProductName(productData.getProductName());
                products.add(product);
            }
            Product[] array = new Product[products.size()];
            for  (int i=0; i<products.size(); i++){
                array[i] = products.get(i);
            }
            return array;
        }
    @Override
    public Product get(Integer id) {
        log.info(" Input id >> "+  Integer.toString(id) );
        Product product = null;
        Optional<ProductData> optional = productDataRepository.findById(id);
        if(optional.isPresent()) {
            log.info(" Is present >> ");
            product = new Product();
            product.setId(optional.get().getId());
            product.setProductName(optional.get().getProductName());
        }
        else {
            log.info(" Failed >> unable to locate id: " +  Integer.toString(id)  );
        }
        return product;
    }
        @Override
        public Product create(Product product) {
            log.info(" add:Input " + product.toString());
            ProductData productData = new ProductData();
            productData.setProductName(product.getProductName());
            productData.setCategoryName(product.getCategoryName());
            productData.setImageFile(product.getImageFile());
            productData.setPrice(product.getPrice());
            productData = productDataRepository.save(productData);
            log.info(" add:Input " + productData.toString());
            Product newProduct = new Product();
            newProduct.setId(productData.getId());
            newProduct.setProductName(productData.getProductName());

            newProduct.setCategoryName(productData.getCategoryName());
            newProduct.setPrice(productData.getPrice());

            newProduct.setImageFile(productData.getImageFile());
            newProduct.setQuantity(productData.getQuantity());
            return newProduct;
        }

        @Override
        public Product update(Product product) {
            Product updatedProduct = null;
            int id = product.getId();
            Optional<ProductData> optional  = productDataRepository.findById(product.getId());
            if(optional.isPresent()){
                ProductData originalProductData = new ProductData();
                originalProductData.setId(product.getId());
                originalProductData.setProductName(product.getProductName());
                originalProductData.setCategoryName(product.getCategoryName());
                originalProductData.setImageFile(product.getImageFile());
                originalProductData.setQuantity(product.getQuantity());
                ProductData productData = productDataRepository.save(originalProductData);
                return product;
            }
            else {
                log.error("Product record with id: " + Integer.toString(id) + " do not exist ");

            }
            return updatedProduct;
        }
    @Override
    public void delete(Integer id) {
        Product product = null;
        log.info(" Input >> " +  Integer.toString(id));
        Optional<ProductData> optional = productDataRepository.findById(id);
        if( optional.isPresent()) {
            ProductData productDatum = optional.get();
            productDataRepository.delete(optional.get());
            log.info(" Successfully deleted Product record with id: " + Integer.toString(id));
        }
        else {
            log.error(" Unable to locate product with id:" +  Integer.toString(id));
        }
    }


}
