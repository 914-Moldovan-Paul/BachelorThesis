import React, { useState } from 'react';
import { Routes, Route, useNavigate } from 'react-router-dom';
import Navbar from './components/Navbar';
import Login from './components/Login';
import CarListings from './components/CarListings';
import CreateCarListing from './components/CreateCarListing';
import { setAuthHeader } from './helpers/AxiosHelper';
import CarListingDetails from './components/CarListingDetails';

const AppContent = () => {
    const [isAuthenticated, setAuthenticated] = useState(false);
    const [user, setUser] = useState(null);

    const navigate = useNavigate();

    const handleLogout = () => {
        setAuthenticated(false);
        setUser(null);
        setAuthHeader(null);
        navigate('/cars');
    };

    return (
        <>
            <Navbar isAuthenticated={isAuthenticated} user={user} handleLogout={handleLogout} />
            <Routes>
                <Route path="/login" element={<Login setAuthenticated={setAuthenticated} setUser={setUser} />} />
                <Route path="/signup" element={<Login setAuthenticated={setAuthenticated} setUser={setUser} />} />
                <Route path="/create-listing" element={<CreateCarListing user={user} isUpdate={false}/>} />
                <Route path="/edit-listing/:carId" element={<CreateCarListing user={user} isUpdate={true} />} />
                <Route path="/cars" element={<CarListings isMainPage={true}/>} />
                <Route path="/my-listings" element={<CarListings isMainPage={false} user={user}/>} />
                <Route path="/car-listing-details/:carListingId" element={<CarListingDetails />} />
            </Routes>
        </>
    );
};

export default AppContent;
