import { BrowserRouter, Route, Routes } from 'react-router-dom';
import BookCarousel from './components/book/BookCarousel';

import Home from './pages/Home';

import GlobalStyle from './styles/GlobalStyle';

function App() {
  return (
    <>
      <GlobalStyle />
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/book" element={<BookCarousel />} />
        </Routes>
      </BrowserRouter>
    </>
  );
}

export default App;
