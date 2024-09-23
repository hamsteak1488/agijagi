import { BrowserRouter, Route, Routes } from 'react-router-dom';
import BookCarousel from './components/book/BookCarousel';

import Home from './pages/Home';
import ActivityRecord from './pages/ActivityRecord';

import GlobalStyle from './styles/GlobalStyle';
import { ModalProvider } from './hooks/useModal';

function App() {
  return (
    <>
      <GlobalStyle />
      <ModalProvider>
        <BrowserRouter>
          <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/book" element={<BookCarousel />} />
            <Route path="/activity-record" element={<ActivityRecord />} />
          </Routes>
        </BrowserRouter>
      </ModalProvider>
    </>
  );
}

export default App;
