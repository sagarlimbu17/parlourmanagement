
var cart=[];

function Item(category,name,price,quantity,vendor,totalPrice,salesType){
        this.category= category,
        this.name=name,
        this.price=price,
        this.quantity=quantity,
        this.vendor=vendor,
        this.totalPrice= totalPrice,
        this.salesType=salesType;
}

function addProductToCart(event){
    event.preventDefault();

    var billingType=document.querySelector("#salesType");
    var billingTypeValue = billingType.options[billingType.selectedIndex].value;
    console.log(billingTypeValue);

    var name=document.querySelector("#productName").value;
    console.log(name);
    var price = document.querySelector("#productPrice").value;
    var quantity= parseInt(document.querySelector("#productQuantity").value);
    var vendor = document.querySelector("#productVendor").value;

    var category=document.querySelector("#category");
    var categoryValue = category.options[category.selectedIndex].value;
    console.log("printed category value:"+categoryValue);
    var totalPrice = price*quantity;

    var cartItem = new Item(categoryValue,name,price,quantity,vendor,totalPrice,billingTypeValue);
    console.log("printed cart item " +cartItem.category);

    console.log("cart items are:" +cartItem.name);

    for(var i in cart){
        if(cart[i].name===name){
            cart[i].quantity+=quantity;
            cart[i].totalPrice+=totalPrice;
            displayCart();
            return;
        }
    }
    cart.push(cartItem);
    console.log(cart);
    saveCartToLocal();
    displayCart();
    displayCheckoutButton();i

}

function grandTotal() {
    var grandTotal=0;
    for(var i in cart){
        grandTotal+=cart[i].totalPrice;
    }
    return grandTotal;
}

function  displayCart() {
    var cartArray = listCart();
    var output="";
    for(var i in cartArray){
        output+=
            `<tr> 
                <td id="itemName">`+cartArray[i].name+`</td>
                <td>`+cartArray[i].price+`</td>
                <td>`+cartArray[i].quantity+`</td>
                <td>`+cartArray[i].totalPrice+`</td>
                <td>
                <button 
                 onclick="removeItem(this)" class="btn btn-danger btn-sm btn-remove"><span class='glyphicon glyphicon-remove'></span></button>
                </td>
            </tr>`;
    }
    var grandTotalValue = grandTotal();

    document.getElementById("table_body").innerHTML=output;
    document.getElementById("grand_total").innerHTML="<b>Grand Total:</b> "+grandTotal();

}

function  saveCartToLocal() {
    localStorage.setItem("cart",JSON.stringify(cart));
}

function listCart() {
    var cartCopy=[];
    for(var i in cart){
        var item = cart[i];
        var itemCopy={};
        for(var p in item){
            itemCopy[p]=item[p];
        }
        cartCopy.push(itemCopy);
    }
    return cartCopy;

}

function displayCheckoutButton(){
    var checkoutButton =document.getElementById("check_out");

    if(localStorage.getItem("cart")==null){
        checkoutButton.style.display='none';
    }
    else
        checkoutButton.style.display='block';
}

displayCheckoutButton();

function removeItem(ItemName){
    var confirmButton = document.getElementById("confirmButton");
    var a = document.getElementsByClassName('btn-remove');
    var b = ItemName.parentNode.parentNode.cells[0].textContent;
    console.log(b);
    removeAll(b);
    if(cart.length==0){
        confirmButton.disabled=true;
    }

}

function  removeAll(item) {
    for(var i in cart){
        if(cart[i].name ==item){
            cart.splice(i,1);
            break;
        }
    }

    displayCart();
    viewCartOnModal();
}

function  clearCart() {
    cart=[];
}



function createBills(e){

    e.preventDefault();
    var modal_table =document.getElementById("modal_table");
    var modal_header=document.getElementById("modal_header");
    var confirmButton = document.getElementById("confirmButton");
    var modal_total =document.getElementById("modal_total");
    console.log("we are fetching api")
    fetch('https://allinonemgmtapp.herokuapp.com/createBill',{
        method:'POST',
        headers:{
            'Accept':'application/json, text/plain, */*',
            'Content-type':'application/json'
        },
        body:JSON.stringify(cart)
    })
        .then(function(response){
            if(response.status===200){
              document.getElementById("msg").style.display='block';
              modal_table.remove();
              modal_header.remove();
              confirmButton.remove();
              modal_total.remove();
              clearCart();
              viewCartOnModal();
            }
        })
}

function viewCartOnModal(e){

    var cartArray = listCart();
    var output="";
    for(var i in cartArray){
        output+=
            `<tr> 
                <td id="itemName">`+cartArray[i].name+`</td>
                <td>`+cartArray[i].price+`</td>
                <td>`+cartArray[i].quantity+`</td>
                <td>`+cartArray[i].totalPrice+`</td>
                <td>
                <button 
                 onclick="removeItem(this)" class="btn btn-danger btn-sm btn-remove"><span class='glyphicon glyphicon-remove'></span></button>
                </td>
            </tr>`;
    }
    var grandTotalValue = grandTotal();

    document.getElementById("modal_cart").innerHTML=output;
    document.getElementById("modal_total").innerHTML="<b>Grand Total:</b> "+grandTotal();
}

