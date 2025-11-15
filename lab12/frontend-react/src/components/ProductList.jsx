import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import axios from '../api';

export default function ProductList() {
    const [productos, setProductos] = useState([]);

    useEffect(() => {
        cargarProductos();
    }, []);

    const cargarProductos = async () => {
        const res = await axios.get('/productos');
        setProductos(res.data);
    };

    const eliminarProducto = async (id) => {
        if (window.confirm('¿Deseas eliminar este producto?')) {
            await axios.delete(`/productos/${id}`);
            cargarProductos();
        }
    };

    return (
        <div>
            <div className="d-flex justify-content-between align-items-center mb-3">
                <h4>Lista de Productos</h4>
                <Link to="/productos/new" className="btn btn-primary">Nuevo Producto</Link>
            </div>
            <table className="table table-striped table-hover">
                <thead className="table-dark">
                    <tr>
                        <th>ID</th>
                        <th>Nombre</th>
                        <th>Precio</th>
                        <th>Stock</th>
                        <th>Categoría</th>
                        <th>Acciones</th>
                    </tr>
                </thead>
                <tbody>
                    {productos.map((prod) => (
                        <tr key={prod.id}>
                            <td>{prod.id}</td>
                            <td>{prod.nombre}</td>
                            <td>S/ {prod.precio}</td>
                            <td>{prod.stock}</td>
                            <td>{prod.categoria?.nombre}</td>
                            <td>
                                <Link to={`/productos/edit/${prod.id}`} className="btn btn-sm btn-warning me-2">Editar</Link>
                                <button className="btn btn-sm btn-danger" onClick={() => eliminarProducto(prod.id)}>Eliminar</button>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
}
