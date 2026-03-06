export interface RawMaterial {
  id: number
  name: string
  quantity: number
}

export interface RawMaterialForm {
  name: string
  quantity: number
}

export interface ProductIngredient {
  id: number
  rawMaterial: RawMaterial
  quantity: number
}

export interface ProductIngredientForm {
  rawMaterialId: number | null
  quantity: number
}

export interface Product {
  id: number
  name: string
  price: number
  rawMaterials: ProductIngredient[]
}

export interface ProductForm {
  name: string
  price: number
  rawMaterials: ProductIngredientForm[]
}