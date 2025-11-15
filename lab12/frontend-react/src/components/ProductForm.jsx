import React, { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import axios from '../api';

export default function ProductForm() {
    const [producto, setProducto] = useState({
        nombre: '',
        precio: '',
        stock: '',
        categoriaId: ''
    });
    const [categorias, setCategorias] = useState([]);
    const { id } = useParams();
    const navigate = useNavigate();

    useEffect(() => {
        cargarCategorias();
        if (id) cargarProducto();
    }, [id]);

    const cargarCategorias = async () => {
        const res = await axios.get('/categorias');
        setCategorias(res.data);
    };

    const cargarProducto = async () => {
        const res = await axios.get(`/productos/${id}`);
        const data = res.data;
        setProducto({
            id: data.id,
            nombre: data.nombre,
            precio: data.precio,
            stock: data.stock,
            categoriaId: data.categoria?.id || ''
        });
    };

    const handleChange = (e) => {
        setProducto({ ...producto, [e.target.name]: e.target.value });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        const productoData = {
            nombre: producto.nombre,
            precio: parseFloat(producto.precio),
            stock: parseInt(producto.stock),
            categoria: { id: parseInt(producto.categoriaId) }
        };

        if (id) {
            await axios.put(`/productos/${id}`, productoData);
        } else {
            await axios.post('/productos', productoData);
        }

        navigate('/productos');
    };

    return (
        <div>
            <h4>{id ? 'Editar Producto' : 'Nuevo Producto'}</h4>
            <form onSubmit={handleSubmit}>
                <div className="mb-3">
                    <label className="form-label">Nombre</label>
                    <input
                        type="text"
                        className="form-control"
                        name="nombre"
                        value={producto.nombre}
                        onChange={handleChange}
                        required
                    />
                </div>

                <div className="mb-3">
                    <label className="form-label">Precio</label>
                    <input
                        type="number"
                        step="0.01"
                        className="form-control"
                        name="precio"
                        value={producto.precio}
                        onChange={handleChange}
                        required
                    />
                </div>

                <div className="mb-3">
                    <label className="form-label">Stock</label>
                    <input
                        type="number"
                        className="form-control"
                        name="stock"
                        value={producto.stock}
                        onChange={handleChange}
                        required
                    />
                </div>

                <div className="mb-3">
                    <label className="form-label">Categoría</label>
                    <select
                        name="categoriaId"
                        className="form-select"
                        value={producto.categoriaId}
                        onChange={handleChange}
                        required
                    >
                        <option value="">Seleccione una categoría</option>
                        {categorias.map((cat) => (
                            <option key={cat.id} value={cat.id}>
                                {cat.nombre}
                            </option>
                        ))}
                    </select>
                </div>

                <button type="submit" className="btn btn-success">Guardar</button>
                <button type="button" className="btn btn-secondary ms-2" onClick={() => navigate('/productos')}>
                    Cancelar
                </button>
            </form>
        </div>
    );
}
