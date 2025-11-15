import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import axios from '../api';

export default function CategoryList() {
    const [categorias, setCategorias] = useState([]);

    useEffect(() => {
        cargarCategorias();
    }, []);

    const cargarCategorias = async () => {
        const res = await axios.get('/categorias');
        setCategorias(res.data);
    };

    const eliminarCategoria = async (id) => {
        if (window.confirm('¿Deseas eliminar esta categoría?')) {
            await axios.delete(`/categorias/${id}`);
            cargarCategorias();
        }
    };

    return (
        <div>
            <div className="d-flex justify-content-between align-items-center mb-3">
                <h4>Lista de Categorías</h4>
                <Link to="/categorias/new" className="btn btn-primary">Nueva Categoría</Link>
            </div>
            <table className="table table-striped table-hover">
                <thead className="table-dark">
                    <tr>
                        <th>ID</th>
                        <th>Nombre</th>
                        <th>Descripción</th>
                        <th>Acciones</th>
                    </tr>
                </thead>
                <tbody>
                    {categorias.map((cat) => (
                        <tr key={cat.id}>
                            <td>{cat.id}</td>
                            <td>{cat.nombre}</td>
                            <td>{cat.descripcion}</td>
                            <td>
                                <Link to={`/categorias/edit/${cat.id}`} className="btn btn-sm btn-warning me-2">Editar</Link>
                                <button className="btn btn-sm btn-danger" onClick={() => eliminarCategoria(cat.id)}>Eliminar</button>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
}
