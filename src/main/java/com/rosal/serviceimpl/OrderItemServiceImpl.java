package com.rosal.serviceimpl;

import com.rosal.entity.OrderItemData;
import com.rosal.model.OrderItem;

import com.rosal.repostory.OrderItemDataRepository;
import com.rosal.service.OrderItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;


@Slf4j
@Service
public class OrderItemServiceImpl implements OrderItemService {

    @Autowired
    OrderItemDataRepository orderItemDataRepository;

    @Override
    public List<OrderItem> getOrderItems() {
        List<OrderItemData> orderItemsData = new ArrayList<>();
        List<OrderItem> orderItems = new ArrayList<>();
        orderItemDataRepository.findAll().forEach(orderItemsData::add);
        Iterator<OrderItemData> it = orderItemsData.iterator();
        while (it.hasNext()) {
            OrderItemData orderItemData = it.next();
            OrderItem orderItem = new OrderItem();
            orderItem.setId(orderItemData.getId());

            //User Data
           // orderItem.setOrderId(orderItemData.getOrderId());
            orderItem.setUserId(orderItemData.getUserId());
            orderItem.setUserName(orderItemData.getUserName());

            //Product Data
            orderItem.setProductId(orderItemData.getProductId());
            orderItem.setProductName(orderItemData.getProductName());
            orderItem.setImageFile(orderItemData.getImageFile());
            orderItem.setQuantity(orderItemData.getQuantity());
            orderItem.setPrice(orderItemData.getPrice());
            orderItem.setStatus(orderItemData.getStatus());
            orderItem.setOrderId(orderItemData.getOrderId());






            //  orderItem.setName(orderItemData.getName());
            //   orderItem.setDescription(orderItemData.getDescription());
            //  orderItem.setRouterPath(orderItemData.getRouterPath());
            //   orderItem.setCategoryName(orderItemData.getCategoryName());
            orderItems.add(orderItem);
        }
        return orderItems;
    }

    @Override
    public OrderItem create(OrderItem orderItem) {
        log.info(" add:Input " + orderItem.toString());
        OrderItemData orderItemData = new OrderItemData();

        //User Data
      //  orderItemData.setOrderId(orderItem.getOrderId());
        orderItemData.setUserId(orderItem.getUserId());
        orderItemData.setUserName(orderItem.getUserName());
        //Product Data
        orderItemData.setProductId(orderItem.getProductId());
        orderItemData.setProductName(orderItem.getProductName());
        orderItemData.setImageFile(orderItem.getImageFile());
        orderItemData.setQuantity(orderItem.getQuantity());
        orderItemData.setPrice(orderItem.getPrice());
        orderItemData.setStatus(orderItem.getStatus());
        orderItemData.setOrderId(orderItem.getOrderId());


        orderItemData = orderItemDataRepository.save(orderItemData);
        log.info(" add:Input " + orderItemData.toString());
        OrderItem newOrderItem = new OrderItem();
        newOrderItem.setId(orderItemData.getId());

        //User Data
        //newOrderItem.setOrderId(orderItemData.getOrderId());
        newOrderItem.setUserId(orderItemData.getUserId());
        newOrderItem.setUserName(orderItemData.getUserName());
        //Product Data
        newOrderItem.setProductId(orderItemData.getProductId());
        newOrderItem.setProductName(orderItemData.getProductName());
        newOrderItem.setImageFile(orderItemData.getImageFile());
        newOrderItem.setQuantity(orderItemData.getQuantity());
        newOrderItem.setPrice(orderItemData.getPrice());
        newOrderItem.setStatus(orderItemData.getStatus());
        newOrderItem.setOrderId(orderItemData.getOrderId());

        return newOrderItem;
    }

    @Override
    public OrderItem update(int id, OrderItem orderItem) {
        Optional<OrderItemData> optional = orderItemDataRepository.findById(id);

        if (optional.isPresent()) {
            OrderItemData originalOrderItemData = optional.get();

            // Updating fields with new values
          //  originalOrderItemData.setOrderId(orderItem.getOrderId());
            originalOrderItemData.setUserId(orderItem.getUserId());
            originalOrderItemData.setUserName(orderItem.getUserName());
            originalOrderItemData.setProductId(orderItem.getProductId());
            originalOrderItemData.setProductName(orderItem.getProductName());
            originalOrderItemData.setImageFile(orderItem.getImageFile());
            originalOrderItemData.setQuantity(orderItem.getQuantity());
            originalOrderItemData.setPrice(orderItem.getPrice());
            originalOrderItemData.setStatus(orderItem.getStatus());
            originalOrderItemData.setOrderId(orderItem.getOrderId());

            // Save the updated entity
            originalOrderItemData = orderItemDataRepository.save(originalOrderItemData);

            // Convert the updated OrderItemData back to OrderItem, if necessary
            OrderItem updatedOrderItem = new OrderItem();
            updatedOrderItem.setId(originalOrderItemData.getId());
          //  updatedOrderItem.setOrderId(originalOrderItemData.getOrderId());
            updatedOrderItem.setUserId(originalOrderItemData.getUserId());
            updatedOrderItem.setUserName(originalOrderItemData.getUserName());
            updatedOrderItem.setProductId(originalOrderItemData.getProductId());
            updatedOrderItem.setProductName(originalOrderItemData.getProductName());
            updatedOrderItem.setImageFile(originalOrderItemData.getImageFile());
            updatedOrderItem.setQuantity(originalOrderItemData.getQuantity());
            updatedOrderItem.setPrice(originalOrderItemData.getPrice());
            updatedOrderItem.setStatus(originalOrderItemData.getStatus());
            updatedOrderItem.setOrderId(originalOrderItemData.getOrderId());

            return updatedOrderItem;

        } else {
            log.error("OrderItem record with id: " + id + " does not exist");
            throw new EntityNotFoundException("OrderItem with id " + id + " not found");
        }
    }



    @Override
    public void delete(Integer id) {
        OrderItem orderItem = null;
        log.info(" Input >> " + Integer.toString(id));
        Optional<OrderItemData> optional = orderItemDataRepository.findById(id);
        if (optional.isPresent()) {
            OrderItemData orderItemDatum = optional.get();
            orderItemDataRepository.delete(optional.get());
            log.info(" Successfully deleted OrderItem record with id: " + Integer.toString(id));
        } else {
            log.error(" Unable to locate orderItem with id:" + Integer.toString(id));
        }
    }




}




