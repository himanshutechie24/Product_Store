import { create } from "zustand";

export const useProductStore = create((set) => ({
  products: [],
  setProducts: (products) => set({ products }),
  createProduct: async (newProduct) => {
    if (!newProduct.name || !newProduct.image || !newProduct.number) {
      return { success: false, message: "Please fill in all fields." };
    }

    try {
      const res = await fetch("/api/products/", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(newProduct),
      });

      const text = await res.text();

      if (!res.ok) {
        let message;
        try {
          const error = JSON.parse(text);
          message = error?.error || "Something went wrong.";
        } catch {
          message = text;
        }
        return { success: false, message };
      }

      const data = JSON.parse(text);
      set((state) => ({ products: [...state.products, data] }));
      return { success: true, message: "Product Created Successfully!" };
    } catch (err) {
      return { success: false, message: "Network error" };
    }
  },
  fetchProducts: async () => {
  try {
    const res = await fetch("/api/products/");

    if (!res.ok) {
      const text = await res.text();
      let message;
      try {
        const error = JSON.parse(text);
        message = error?.error || "Something went wrong.";
      } catch {
        message = text;
      }
      // Set empty array on error (e.g., 404)
      set({ products: [] });
      return { success: false, message };
    }

    const data = await res.json();
    if (Array.isArray(data)) {
      set({ products: data });
      return { success: true };
    } else {
      set({ products: [] });
      return { success: false, message: "Unexpected response format." };
    }
  } catch (err) {
    set({ products: [] });
    return { success: false, message: "Network error" };
  }
},
  deleteProduct: async(pid)=>{
    console.log(pid);
    const res= await fetch(`/api/products/${pid}`,{
      method: "DELETE",
    });
    const text = await res.text(); // Read as plain text, not JSON

    if (!res.ok) {
      return { success: false, message: text };
    }
    set((state) => ({
      products: state.products.filter((product) => product.id !== pid),
    }));

    return { success: true, message: text };
  },
  updateProduct: async(pid,updatedProduct)=>{
    const res= await fetch(`/api/products/${pid}`,{
      method:"PUT",
      headers:{
        "Content-Type":"application/json",
      },
      body: JSON.stringify(updatedProduct),
    });
     const data = await res.json();// Read as plain text, not JSON

    if (!res.ok) {
      return { success: false, message: data.message || "Update failed" };
    }
    //without need refresh
    set((state) => ({
      products: state.products.map((product) => product.id === pid? data:product),
    }));

    return { success: true, message: data.message || "Updated Successfully" };
  }
}));
