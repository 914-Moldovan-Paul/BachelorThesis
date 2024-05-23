import React, { useState } from 'react';

const SearchBox = ({ onSubmit }) => {
  const [query, setQuery] = useState('');

  const handleSubmit = () => {
    onSubmit(query);
  };

  return (
    <div className="max-w-md mx-auto mt-10">
      <textarea
        className="w-full h-24 p-4 text-gray-800 bg-gray-100 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500 resize-none overflow-auto"
        style={{ maxHeight: '8rem' }}
        placeholder="Type your search here..."
        value={query}
        onChange={(e) => setQuery(e.target.value)}
      />
      <button
        className="mt-4 px-4 py-2 bg-blue-500 text-white rounded-lg hover:bg-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-opacity-50"
        onClick={handleSubmit}
      >
        Submit
      </button>
    </div>
  );
};

export default SearchBox;
