package sanssys.solution.pssolutions.modal
data class ItemResponse(
    var data: ProductList,
    var error:String,
    var status: Boolean
)
data class BrandResponse(
    var data: BrandData,
    var error:String,
    var status: Boolean
)

data class BrandData (
    var brands: ArrayList<Brand>
)

data class data (
    var item: ArrayList<Product>
)

data class ProductList (
    var productList: ArrayList<Item>
)

data class Product (
    var id:Int,
    var serialNumber: Number,
    var name: String,
    var measurement: String,
    var type: String,
    var image:String,
    var createdAt: String,
    var updatedAt:String,
    var stocks: ArrayList<ProductItem>,

)

data class Brand (
    var id:Int,
    var name: String
    )

data class Item (
    var id:Int,
    var name: String,
    var category: String,
    var brand: String,
    var quantity: String,
    var price: Int,
    var discount: Int,
    var ourPrice: Int,
    var image:String,
    var createdAt: String,
    var updatedAt:String,
    var stock: Int =0,
    var cart: Int = 0,
    var add:Int = 0
)
data class ProductItem(
    var id:Int,
    var discount:Float,
    var price:Float,
    var size: Int,
    var rating: Int,
    var quantity:Int,
    var sizeItemId:String,
    var createdAt: String,
    var updatedAt:String,
    var itemId:Int
)