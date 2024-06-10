import React, { useState, useEffect } from 'react';
import { useNavigate, Link, useLocation } from 'react-router-dom';
import { request, setAuthHeader } from '../helpers/AxiosHelper';

const Login = ({ setAuthenticated, setUser }) => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [firstName, setFirstName] = useState('');
    const [lastName, setLastName] = useState('');
    const [isSignup, setIsSignup] = useState(false);
    const [errors, setErrors] = useState({});
    const [formError, setFormError] = useState('');
    const navigate = useNavigate();
    const location = useLocation();

    useEffect(() => {
        setIsSignup(location.pathname === '/signup');
        resetForm();
    }, [location.pathname]);

    const resetForm = () => {
        setUsername('');
        setPassword('');
        setFirstName('');
        setLastName('');
        setErrors({});
        setFormError('');
    };

    const validateForm = () => {
        const newErrors = {};

        const nameRegex = /^[A-Za-z]{3,15}$/;
        const usernameRegex = /^(?=.*[a-zA-Z])[a-zA-Z0-9]{3,15}$/;
        const passwordRegex = /^(?=.*[a-zA-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;

        if (isSignup) {
            if (!nameRegex.test(firstName)) {
                newErrors.firstName = 'First name should be at least 3 characters long and contain only letters.';
            }
            if (!nameRegex.test(lastName)) {
                newErrors.lastName = 'Last name should be at least 3 characters long and  contain only letters.';
            }
            if (!usernameRegex.test(username)) {
                newErrors.username = 'Username should be at least 3 characters long and include only letters and numbers.';
            }
            if (!passwordRegex.test(password)) {
                newErrors.password = 'Password should be minimum 8 characters, include letters, numbers, and at least 1 special character.';
            }
        }

        setErrors(newErrors);
        return Object.keys(newErrors).length === 0;
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        if (!validateForm()) return;

        try {
            if (isSignup) {
                const response = await request('post', '/register', { username, password, firstName, lastName });
                const user = response.data;
                setAuthHeader(user.token);
                setAuthenticated(true);
                setUser(user);
                navigate('/cars');
            } else {
                const response = await request('post', '/login', { username, password });
                const user = response.data;
                setAuthHeader(user.token);
                setAuthenticated(true);
                setUser(user);
                navigate('/cars');
            }
        } catch (error) {
            console.error('Submission failed:', error);
            if (error.response.status === 404) {
                setFormError('Invalid credentials. Please try again.');
            } else {
                setFormError('An error occurred. Please try again later.');
            }
        }
    };

    return (
        <section className="bg-gray-50">
            <div className="flex flex-col items-center justify-center px-6 py-8 mx-auto md:h-screen lg:py-0">
                <div className="w-full bg-white rounded-lg shadow md:mt-0 sm:max-w-md xl:p-0">
                    <div className="p-6 space-y-4 md:space-y-6 sm:p-8">
                        <h1 className="text-xl font-bold leading-tight tracking-tight text-gray-900 md:text-2xl">
                            {isSignup ? 'Sign up for an account' : 'Log in to your account'}
                        </h1>
                        {formError && <p className="text-red-500 text-sm">{formError}</p>}
                        <form className="space-y-4 md:space-y-6" onSubmit={handleSubmit}>
                            {isSignup && (
                                <>
                                    <div>
                                        <label htmlFor="firstName" className="block mb-2 text-sm font-medium text-gray-900">First Name</label>
                                        <input
                                            type="text"
                                            name="firstName"
                                            id="firstName"
                                            className="bg-gray-50 border border-gray-300 text-gray-900 sm:text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5"
                                            placeholder="John"
                                            value={firstName}
                                            onChange={(e) => setFirstName(e.target.value)}
                                            required
                                            maxLength={15}
                                        />
                                        {errors.firstName && <p className="text-red-500 text-sm">{errors.firstName}</p>}
                                    </div>
                                    <div>
                                        <label htmlFor="lastName" className="block mb-2 text-sm font-medium text-gray-900">Last Name</label>
                                        <input
                                            type="text"
                                            name="lastName"
                                            id="lastName"
                                            className="bg-gray-50 border border-gray-300 text-gray-900 sm:text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5"
                                            placeholder="Doe"
                                            value={lastName}
                                            onChange={(e) => setLastName(e.target.value)}
                                            required
                                            maxLength={15}
                                        />
                                        {errors.lastName && <p className="text-red-500 text-sm">{errors.lastName}</p>}
                                    </div>
                                </>
                            )}
                            <div>
                                <label htmlFor="username" className="block mb-2 text-sm font-medium text-gray-900">Username</label>
                                <input
                                    type="text"
                                    name="username"
                                    id="username"
                                    className="bg-gray-50 border border-gray-300 text-gray-900 sm:text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5"
                                    placeholder="JohnDoe123"
                                    value={username}
                                    onChange={(e) => setUsername(e.target.value)}
                                    required
                                    maxLength={15}
                                />
                                {errors.username && <p className="text-red-500 text-sm">{errors.username}</p>}
                            </div>
                            <div>
                                <label htmlFor="password" className="block mb-2 text-sm font-medium text-gray-900">Password</label>
                                <input
                                    type="password"
                                    name="password"
                                    id="password"
                                    placeholder="••••••••"
                                    className="bg-gray-50 border border-gray-300 text-gray-900 sm:text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5"
                                    value={password}
                                    onChange={(e) => setPassword(e.target.value)}
                                    required
                                />
                                {errors.password && <p className="text-red-500 text-sm">{errors.password}</p>}
                            </div>
                            <button type="submit" className="w-full text-white bg-primary-600 hover:bg-primary-700 focus:ring-4 focus:outline-none focus:ring-primary-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center">
                                {isSignup ? 'Sign up' : 'Sign in'}
                            </button>
                            <p className="text-sm font-light text-gray-500">
                                {isSignup ? (
                                    <>Already have an account? <Link to="/login" className="font-medium text-primary-600 hover:underline">Log in</Link></>
                                ) : (
                                    <>Don’t have an account yet? <Link to="/signup" className="font-medium text-primary-600 hover:underline">Sign up</Link></>
                                )}
                            </p>
                        </form>
                    </div>
                </div>
            </div>
        </section>
    );
};

export default Login;
