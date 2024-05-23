import React, { useState } from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Navbar from './components/Navbar';
import Login from './components/Login';
import Cars from './components/Cars';
import CreateCarListing from './components/CreateCarListing';
import { setAuthHeader } from './helpers/AxiosHelper';

const App = () => {
    const [isAuthenticated, setAuthenticated] = useState(false);
    const [user, setUser] = useState(null);

    const handleLogout = () => {
        setAuthenticated(false);
        setUser(null);
        setAuthHeader(null);
    };

    return (
        <Router>
            <Navbar isAuthenticated={isAuthenticated} user={user} handleLogout={handleLogout} />
            <Routes>
                <Route path="/login" element={<Login setAuthenticated={setAuthenticated} setUser={setUser} />} />
                <Route path="/signup" element={<Login setAuthenticated={setAuthenticated} setUser={setUser} />} />
                <Route path="/create-listing" element={<CreateCarListing user={user}/>} />
                <Route path="/cars" element={<Cars />} />
            </Routes>
        </Router>
    );
};

export default App;
