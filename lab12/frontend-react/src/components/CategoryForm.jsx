import React, { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import axios from '../api';

export default function CategoryForm() {
    const [categoria, setCategoria] = useState({ nombre: '' });
    const { id } = useParams();
    const navigate = useNavigate();

    useEffect(() => {
        if (id) cargarCategoria();
    }, [id]);

    const cargarCategoria = async () => {
        const res = await axios.get(`/categorias/${id}`);
        setCategoria(res.data);
    };

    const handleChange = (e) => {
        setCategoria({ ...categoria, [e.target.name]: e.target.value });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        if (id) {
            await axios.put(`/categorias/${id}`, categoria);
        } else {
            await axios.post('/categorias', categoria);
        }
        navigate('/categorias');
    };

    return (
        <div>
            <h4>{id ? 'Editar Categoría' : 'Nueva Categoría'}</h4>
            <form onSubmit={handleSubmit}>
                <div className="mb-3">
                    <label className="form-label">Nombre</label>
                    <input
                        type="text"
                        className="form-control"
                        name="nombre"
                        value={categoria.nombre}
                        onChange={handleChange}
                        required
                    />
                </div>

                <div className="mb-3">
                    <label className="form-label">Descripción</label>
                    <input
                        type="text"
                        className="form-control"
                        name="descripcion"
                        value={categoria.descripcion}
                        onChange={handleChange}
                        required
                    />
                </div>

                <button type="submit" className="btn btn-success">Guardar</button>
                <button type="button" className="btn btn-secondary ms-2" onClick={() => navigate('/categorias')}>
                    Cancelar
                </button>
            </form>
        </div>
    );
}
