import { BrowserRouter, Route, Routes } from 'react-router-dom';

import Home from './pages/Home';
import WritingDiary from './pages/WritingDiary';

import GlobalStyle from './styles/GlobalStyle';

function App() {
  return (
    <>
      <GlobalStyle />
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/writing" element={<WritingDiary />} />
        </Routes>
      </BrowserRouter>
    </>
  );
}

export default App;
