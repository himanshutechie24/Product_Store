import { Box, Button, Container, Heading, Input, useColorModeValue, VStack } from "@chakra-ui/react";
import {useState} from 'react';
import { useProductStore } from "../store/product";
import { create } from "zustand";
import { useToast } from "@chakra-ui/react";

const CreatePage = () => {
  const [newProduct, setNewProduct] = useState({
    name: "",
    number: "",
    image: "",
  });
  const toast = useToast();
  const {createProduct}=useProductStore()

  const handleAddProduct= async()=>{
    const{success, message}= await createProduct(newProduct);
   if (success) {
  toast({
    title: "Success",
    description: message,
    status: "success",
    duration: 3000,
    isClosable: true,
  });
} else {
  toast({
    title: "Error",
    description: message,
    status: "error",
    duration: 3000,
    isClosable: true,
  });
}
setNewProduct({name: "", number: "", image:""});

  };
  return (
  <Container maxW={"container.sm"}>
    <VStack spacing={8}>
      <Heading as={"h1"} size={"2xl"} textAlign={"center"} mb={8}>
        Create New Product
      </Heading>
      <Box
      w={"full"} bg={useColorModeValue("white","gray.800")}
      p={6} rounded={"lg"} shadow={"md"}
      >
        <VStack spacing={4}>
          <Input
          placeholder='Product Name'
          name='name'
          value={newProduct.name}
          onChange={(e)=> setNewProduct({...newProduct, name:e.target.value})}
          />
          <Input
          placeholder='Price'
          name='number'
          value={newProduct.number}
          onChange={(e)=> setNewProduct({...newProduct, number:parseInt(e.target.value)})}
          />
          <Input
          placeholder='Image URL'
          name='image'
          value={newProduct.image}
          onChange={(e)=> setNewProduct({...newProduct, image:e.target.value})}
          />
          <Button colorScheme='blue' onClick={handleAddProduct} w={'full'}>Add Product</Button>

        </VStack>

      </Box>

    </VStack>
  </Container>);

};

export default CreatePage;