import React from 'react';
import { Link } from 'react-router-dom';

export default function Home() {
    return (
        <div className="text-center mt-5">
            <h2 className="mb-4">Bienvenido al Sistema de Gestión</h2>
            <p className="lead mb-4">Administra tus productos y categorías de manera sencilla y rápida.</p>

            <div className="d-flex justify-content-center gap-3">
                <Link to="/productos" className="btn btn-primary btn-lg px-4">
                    <i className="bi bi-box-seam me-2"></i> Gestionar Productos
                </Link>
                <Link to="/categorias" className="btn btn-success btn-lg px-4">
                    <i className="bi bi-tags me-2"></i> Gestionar Categorías
                </Link>
            </div>
        </div>
    );
}
