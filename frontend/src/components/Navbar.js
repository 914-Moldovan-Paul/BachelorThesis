import React from 'react';
import { Link } from 'react-router-dom';

const Navbar = ({ isAuthenticated, user, handleLogout }) => {

    return (
        <div className="bg-white shadow sticky top-0 z-50">
            <div className="container mx-auto px-4">
                <div className="flex items-center justify-between py-4 h-2/4">
                    <Link to="/cars">VroomVault</Link>
                    <div className="hidden sm:flex sm:items-center">
                        {isAuthenticated ? (
                            <>
                                <span className="text-gray-800 text-sm font-semibold mr-4">Hello, {user.username}</span>
                                <Link to="/cars" className="text-gray-800 text-sm font-semibold hover:text-blue-600 mr-4">Cars</Link>
                                <Link to="/my-listings" className="text-gray-800 text-sm font-semibold hover:text-blue-600 mr-4">My Listings</Link>
                                <Link to="/create-listing" className="bg-blue-600 text-white text-sm font-semibold border px-4 py-2 rounded-lg hover:text-white hover:border-gray-800 mr-4">Add Listing</Link>
                                <button onClick={handleLogout} className="text-gray-800 text-sm font-semibold hover:text-blue-600">Logout</button>
                            </>
                        ) : (
                            <>
                                <Link to="/cars" className="text-gray-800 text-sm font-semibold hover:text-blue-600 mr-4">Cars</Link>
                                <Link to="/login" className="text-gray-800 text-sm font-semibold hover:text-blue-600 mr-4">Log in</Link>
                                <Link to="/signup" className="bg-blue-600 text-white text-sm font-semibold border px-4 py-2 rounded-lg hover:text-white hover:border-gray-800">Sign up</Link>
                            </>
                        )}
                    </div>
                </div>
                <div className="block sm:hidden bg-white border-t-2 py-2">
                    <div className="flex flex-col">
                        {isAuthenticated ? (
                            <>
                                <span className="text-gray-800 text-sm font-semibold mb-1">Hello, {user.username}</span>
                                <Link to="/cars" className="text-gray-800 text-sm font-semibold hover:text-blue-600 mb-1">Car</Link>
                                <Link to="/my-listings" className="text-gray-800 text-sm font-semibold hover:text-blue-600 mb-1">My Listings</Link>
                                <Link to="/create-listing" className="bg-blue-600 text-white text-sm font-semibold border px-4 py-2 rounded-lg hover:text-white hover:border-gray-800 mb-1">Add Listing</Link>
                                <button onClick={handleLogout} className="text-gray-800 text-sm font-semibold hover:text-blue-600 mb-1">Logout</button>
                            </>
                        ) : (
                            <>
                                <Link to="/cars" className="text-gray-800 text-sm font-semibold hover:text-blue-600 mb-1">Cars</Link>
                                <Link to="/login" className="text-gray-800 text-sm font-semibold hover:text-blue-600 mb-1">Log in</Link>
                                <Link to="/signup" className="bg-blue-600 text-white text-sm font-semibold border px-4 py-2 rounded-lg hover:text-white hover:border-gray-800 mb-1">Sign up</Link>
                            </>
                        )}
                    </div>
                </div>
            </div>
        </div>
    );
}

export default Navbar;
