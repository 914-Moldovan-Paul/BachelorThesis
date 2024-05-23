import React, { useState } from 'react';
import CarService from '../services/CarService';

const CreateCarListing = ({user}) => {
    const [formData, setFormData] = useState({
        carListingDto: {
            title: '',
            brand: '',
            model: '',
            description: '',
            year: '',
            generation: '',
            vehicleIdentificationNumber: '',
            kilometers: '',
            horsepower: '',
            engineCapacity: '',
            transmission: '',
            gearboxType: '',
            fuelType: '',
            color: '',
            price: '',
            city: '',
            phoneNumber: ''
        },
        userId: user ? user.id : '',
        images: [],
    });

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData(prevFormData => ({
            ...prevFormData,
            carListingDto: {
                ...prevFormData.carListingDto,
                [name]: value,
            },
        }));
    };

    const handleFileChange = (e) => {
        setFormData({
            ...formData,
            images: e.target.files,
        });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        const data = new FormData();
        data.append('carListingDto', new Blob([JSON
            .stringify(formData.carListingDto)], {
            type: 'application/json'
        }));
        for (let i = 0; i < formData.images.length; i++) {
            data.append('images', formData.images[i]);
        }
        data.append('userId', formData.userId);

        try {
            await CarService.createCarListing(data);
        } catch (error) {
            console.error('Error creating car listing:', error);
        }
    };

    return (
        <section>
            <div className="py-8 px-4 mx-auto max-w-2xl lg:py-16">
                <h2 className="mb-4 text-xl font-bold text-gray-900">Add a new car listing</h2>
                <form onSubmit={handleSubmit}>
                    <div className="grid gap-4 sm:grid-cols-2 sm:gap-6">
                        <div className="sm:col-span-2">
                            <label htmlFor="title" className="block mb-2 text-sm font-medium text-gray-900">Title</label>
                            <input type="text" name="title" id="title" value={formData.carListingDto.title} onChange={handleChange} className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5" placeholder="Type product name" required />
                        </div>
                        <div className="w-full">
                            <label htmlFor="brand" className="block mb-2 text-sm font-medium text-gray-900">Brand</label>
                            <input type="text" name="brand" id="brand" value={formData.carListingDto.brand} onChange={handleChange} className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5" placeholder="Car brand" required />
                        </div>
                        <div className="w-full">
                            <label htmlFor="model" className="block mb-2 text-sm font-medium text-gray-900">Model</label>
                            <input type="text" name="model" id="model" value={formData.carListingDto.model} onChange={handleChange} className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5" placeholder="Car model" required />
                        </div>
                        <div className="sm:col-span-2">
                            <label htmlFor="description" className="block mb-2 text-sm font-medium text-gray-900">Description</label>
                            <textarea id="description" name="description" value={formData.carListingDto.description} onChange={handleChange} rows="8" className="block p-2.5 w-full text-sm text-gray-900 bg-gray-50 rounded-lg border border-gray-300 focus:ring-primary-500 focus:border-primary-500" placeholder="Your description here"></textarea>
                        </div>
                        <div className="w-full">
                            <label htmlFor="year" className="block mb-2 text-sm font-medium text-gray-900">Year</label>
                            <input type="number" name="year" id="year" value={formData.carListingDto.year} onChange={handleChange} className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5" placeholder="Year of manufacture" required />
                        </div>
                        <div className="w-full">
                            <label htmlFor="generation" className="block mb-2 text-sm font-medium text-gray-900">Generation</label>
                            <input type="text" name="generation" id="generation" value={formData.carListingDto.generation} onChange={handleChange} className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5" placeholder="Car generation" />
                        </div>
                        <div className="w-full">
                            <label htmlFor="vehicleIdentificationNumber" className="block mb-2 text-sm font-medium text-gray-900">Vehicle Identification Number (VIN)</label>
                            <input type="text" name="vehicleIdentificationNumber" id="vehicleIdentificationNumber" value={formData.carListingDto.vehicleIdentificationNumber} onChange={handleChange} className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5" placeholder="VIN" required />
                        </div>
                        <div className="w-full">
                            <label htmlFor="kilometers" className="block mb-2 text-sm font-medium text-gray-900">Kilometers</label>
                            <input type="number" name="kilometers" id="kilometers" value={formData.carListingDto.kilometers} onChange={handleChange} className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5" placeholder="Kilometers driven" required />
                        </div>
                        <div className="w-full">
                            <label htmlFor="horsepower" className="block mb-2 text-sm font-medium text-gray-900">Horsepower</label>
                            <input type="number" name="horsepower" id="horsepower" value={formData.carListingDto.horsepower} onChange={handleChange} className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5" placeholder="Horsepower" required />
                        </div>
                        <div className="w-full">
                            <label htmlFor="engineCapacity" className="block mb-2 text-sm font-medium text-gray-900">Engine Capacity (CC)</label>
                            <input type="number" name="engineCapacity" id="engineCapacity" value={formData.carListingDto.engineCapacity} onChange={handleChange} className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5" placeholder="Engine capacity in CC" required />
                        </div>
                        <div className="w-full">
                            <label htmlFor="transmission" className="block mb-2 text-sm font-medium text-gray-900">Transmission</label>
                            <input type="text" name="transmission" id="transmission" value={formData.carListingDto.transmission} onChange={handleChange} className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5" placeholder="Transmission type" required />
                        </div>
                        <div className="w-full">
                            <label htmlFor="gearboxType" className="block mb-2 text-sm font-medium text-gray-900">Gearbox Type</label>
                            <input type="text" name="gearboxType" id="gearboxType" value={formData.carListingDto.gearboxType} onChange={handleChange} className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5" placeholder="Gearbox type" required />
                        </div>
                        <div className="w-full">
                            <label htmlFor="fuelType" className="block mb-2 text-sm font-medium text-gray-900">Fuel Type</label>
                            <input type="text" name="fuelType" id="fuelType" value={formData.carListingDto.fuelType} onChange={handleChange} className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5" placeholder="Fuel type" required />
                        </div>
                        <div className="w-full">
                            <label htmlFor="color" className="block mb-2 text-sm font-medium text-gray-900">Color</label>
                            <input type="text" name="color" id="color" value={formData.carListingDto.color} onChange={handleChange} className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5" placeholder="Color" required />
                        </div>
                        <div className="w-full">
                            <label htmlFor="price" className="block mb-2 text-sm font-medium text-gray-900">Price</label>
                            <input type="number" name="price" id="price" value={formData.carListingDto.price} onChange={handleChange} className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5" placeholder="Price" required />
                        </div>
                        <div className="w-full">
                            <label htmlFor="city" className="block mb-2 text-sm font-medium text-gray-900">City</label>
                            <input type="text" name="city" id="city" value={formData.carListingDto.city} onChange={handleChange} className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5" placeholder="City" required />
                        </div>
                        <div className="w-full">
                            <label htmlFor="phoneNumber" className="block mb-2 text-sm font-medium text-gray-900">Phone Number</label>
                            <input type="text" name="phoneNumber" id="phoneNumber" value={formData.carListingDto.phoneNumber} onChange={handleChange} className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5" placeholder="Phone number" required />
                        </div>
                    </div>
                    <div className="sm:col-span-2">
                        <label htmlFor="images" className="block mb-2 text-sm font-medium text-gray-900">Upload Images</label>
                        <input type="file" name="images" id="images" accept="image/png, image/jpeg" className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5" multiple onChange={handleFileChange} />
                    </div>
                    <button type="submit" className="inline-flex items-center px-5 py-2.5 mt-4 sm:mt-6 text-sm font-medium text-center text-white bg-primary-700 rounded-lg focus:ring-4 focus:ring-primary-200 hover:bg-primary-800">
                        Submit Listing
                    </button>
                </form>
            </div>
        </section>
    );
};

export default CreateCarListing;
