export class Order {
    id;
    productName;
    postDate;
    costUsd;
    costPln;

    constructor(orderDetails) {
        this.id = orderDetails.id;
        this.productName = orderDetails.productName;
        this.postDate = orderDetails.postDate;
        this.costUsd = orderDetails.costUsd;
        this.costPln = orderDetails.costPln;
    }
}