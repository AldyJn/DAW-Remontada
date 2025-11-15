import React from 'react';
import { BrowserRouter, Routes, Route, Link } from 'react-router-dom';
import ProductList from './components/ProductList';
import ProductForm from './components/ProductForm';
import CategoryList from './components/CategoryList';
import CategoryForm from './components/CategoryForm';
import Home from './components/Home';

export default function App() {
    return (
        <BrowserRouter>
            <nav className="navbar navbar-expand-lg navbar-dark bg-dark fixed-top">
                <div className="container-fluid">
                    <Link className="navbar-brand" to="/">Gestión CRUD</Link>
                    <button
                        className="navbar-toggler"
                        type="button"
                        data-bs-toggle="collapse"
                        data-bs-target="#navbarNav"
                        aria-controls="navbarNav"
                        aria-expanded="false"
                        aria-label="Toggle navigation"
                    >
                        <span className="navbar-toggler-icon"></span>
                    </button>

                    <div className="collapse navbar-collapse" id="navbarNav">
                        <ul className="navbar-nav ms-auto">
                            <li className="nav-item">
                                <Link className="nav-link" to="/">Inicio</Link>
                            </li>
                            <li className="nav-item">
                                <Link className="nav-link" to="/productos">Productos</Link>
                            </li>
                            <li className="nav-item">
                                <Link className="nav-link" to="/categorias">Categorías</Link>
                            </li>
                        </ul>
                    </div>
                </div>
            </nav>

            <div className="container" style={{ marginTop: '80px' }}>
                <Routes>
                    <Route path="/" element={<Home />} />
                    <Route path="/productos" element={<ProductList />} />
                    <Route path="/productos/new" element={<ProductForm />} />
                    <Route path="/productos/edit/:id" element={<ProductForm />} />
                    <Route path="/categorias" element={<CategoryList />} />
                    <Route path="/categorias/new" element={<CategoryForm />} />
                    <Route path="/categorias/edit/:id" element={<CategoryForm />} />
                </Routes>
            </div>

            <footer className="text-center text-muted py-3 mt-4 border-top">
                <small>© 2025 Gestión CRUD Productos y Categorías</small>
            </footer>
        </BrowserRouter>
    );
}
