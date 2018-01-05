
let products = document.getElementById("productName");
let prodPrice = document.getElementById("productPrice");
let prodVendor = document.querySelector("#productVendor");
let productForm = document.querySelector('#productForm');


document.getElementById("category").addEventListener('change',getProductDetails);
products.addEventListener('change',getProductPrice);


function getProductDetails() {

    let category = document.getElementById("category").value;
    if(category =='none'){
        document.querySelector("#productPrice").value= 0;
        document.querySelector("#productVendor").value='no vendor';
    }

    fetch("http://localhost:5080/getProductDetailsByCategory?category="+category)
        .then((res) => res.json())
        .then((data) => {
        console.log(data)
    products.innerHTML = "";
    data.forEach(function (t) {
        let optionBox = document.createElement("option");
        optionBox.text=t.productName;
        optionBox.value= t.productName;
        products.appendChild(optionBox);
    });

    let dt =data.length;
    if(dt>0) {
        prodPrice.value = data[0].sellingPrice;
        prodVendor.value = data[0].productVendor;
    }

})
}

function getProductPrice() {
    let sel = document.querySelector("#productName");
    let selValue = sel.options[sel.selectedIndex].text;
    fetch("http://localhost:5080/getProductByProductName?productName="+selValue)
        .then((res) => res.json())
.then((data) => {
    data.forEach(function (t) {
       console.log(data);
       prodPrice.value=t.productPrice;
       prodVendor.value=t.productVendor;

    });

})
}


