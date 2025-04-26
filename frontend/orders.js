import { Order } from './order-class.js';
import { backendUrl } from './config.js';

const button = document.getElementById('search-orders');
const productNameElement = document.getElementById('product-name');
const dateInputElement = document.getElementById('post-date');
const sortSelectElement = document.getElementById('sort-type');
const sortDirectionElement = document.getElementById('sort-direction');
const loader = document.getElementById('loader');
const errorMessage = document.getElementById('error-message');
const tableBody = document.querySelector("#orders-table tbody");



button.addEventListener('click', () => {
    showLoader();
    const url = createUrl();
    loadOrdersFetch(url);
})

dateInputElement.addEventListener('input', () =>{
    const dateOption = [...sortSelectElement.options].find(opt => opt.value === 'POST_DATE');
    if (dateInputElement.value !== '') {
        if (dateOption) {
            dateOption.style.display = 'none';
            if (sortSelectElement.value === 'POST_DATE') {
                sortSelectElement.value = 'NONE';
            }
        }
    } else {
        if (dateOption) {
            dateOption.style.display = 'block';
        }
    }
}) 

function loadOrdersFetch(url) {
    console.log(url);
    fetch(url)

        .then(response => {
            if (!response.ok) {
                return response.json().then(err => {
                    const customError = new Error(err.message);
                    customError.status = err.status;
                    customError.date = err.date;
                    throw customError;
                })
            } else {
                return response.json()
        }})

        .then (ordersData => {
            loader.style.display = 'none';
            ordersData.forEach(orderDetails => {
                const order = new Order(orderDetails);
                const row = document.createElement("tr");
                row.innerHTML = `
                    <td>${order.productName}</td>
                    <td>${order.postDate}</td>
                    <td>${order.costUsd}</td>
                    <td>${order.costPln}</td>
                `;
                tableBody.appendChild(row);
            });
    }).catch(error => {
        showError(error)
    });
}

function createUrl() {
    const productName = productNameElement.value;
    const postDate = dateInputElement.value;
    const sortSelect = sortSelectElement.value;
    const sortDirection = sortDirectionElement.value

    let url = `${backendUrl}/api/orders`;
    const params = new URLSearchParams();

    if (productName && productName.trim() !== "") {
        params.append("productName", productName);
    }

    if (postDate && postDate.trim() !== "") {
        params.append("postDate", postDate);
    }

    if(sortSelect != "NONE") {
        params.append("sortField", sortSelect);
        params.append("sortDirection", sortDirection);
    }

    if (params.toString()) {
        url += "?" + params.toString();
    }
    return url;
}

function showLoader(){
    tableBody.innerHTML = "";
    errorMessage.style.display = 'none';
    errorMessage.textContent = '';
    loader.style.display = 'block';
}

function showError(error) {
    loader.style.display = 'none';
    createErrorMessage(error);
    errorMessage.style.display = 'block';
}

function createErrorMessage(error){
    errorMessage.innerHTML = `
     ${error.status ? `<strong>Error ${error.status}</strong><br>` : ""}
    Message: ${error.message}<br>
    Time: ${error.date || new Date().toISOString()}
`;
}