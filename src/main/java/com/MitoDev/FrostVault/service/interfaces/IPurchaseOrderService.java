package com.MitoDev.FrostVault.service.interfaces;

import com.MitoDev.FrostVault.model.dto.*;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface IPurchaseOrderService {

    // METODOS NUEVOS
    // Crea un carrito nuevo para el usuario buyer
    PurchaseOrderTotalPriceResponseDTO initializePurchaseOrder();
    // Agrega un producto a la purchase order y calcula el nuevo monto.
    PurchaseOrderTotalPriceResponseDTO addProductToPurchaseOrder(ProductDTO product);
    // Elimina el producto de la purchase order y calcula el nuevo monto.
    PurchaseOrderTotalPriceResponseDTO deleteProductFromPurchaseOrder(ProductDTO product);
    // Ejecuta la purchase order ignorando el pago (agujero para el req 6). Cambia el status a ejecutado.
    PurchaseOrderExecutedDTO executePurchaseOrder();
    // Esto metodo es viejo, se queda. Sirve para listar los productos que estan en la purchase order.
    ProductsReponseDTO getAllProductsInPurchaseOrder();


}
