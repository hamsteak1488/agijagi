import { BrowserRouter, Route, Routes } from 'react-router-dom';

import Home from './pages/Home';
import ActivityRecord from './pages/ActivityRecord';

import GlobalStyle from './styles/GlobalStyle';

function App() {
  return (
    <>
      <GlobalStyle />
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/activity-record" element={<ActivityRecord />} />
        </Routes>
      </BrowserRouter>
    </>
  );
}

export default App;
