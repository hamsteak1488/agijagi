import { BrowserRouter, Route, Routes } from 'react-router-dom';

import HomePage from './pages/HomePage';
import ActivityRecord from './pages/ActivityRecordPage';
import SchedulePage from './pages/SchedulePage';

import GlobalStyle from './styles/GlobalStyle';
import { ModalProvider } from './hooks/useModal';

function App() {
  return (
    <>
      <GlobalStyle />
      <BrowserRouter>
        <ModalProvider>
          <Routes>
            <Route path="/" element={<HomePage />} />
            <Route path="/activity-record" element={<ActivityRecord />} />
            <Route path="/schedule" element={<SchedulePage />} />
          </Routes>
        </ModalProvider>
      </BrowserRouter>
    </>
  );
}

export default App;
