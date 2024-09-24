import { BrowserRouter, Route, Routes } from 'react-router-dom';

import HomePage from './pages/HomePage';
import WritingDiary from './pages/WritingDiary';
import ActivityRecordPage from './pages/ActivityRecordPage';
import SchedulePage from './pages/SchedulePage';
import BabyMain from './pages/BabyMain';

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
            <Route path="/activity-record" element={<ActivityRecordPage />} />
            <Route path="/baby" element={<BabyMain />} />
            <Route path="/schedule" element={<SchedulePage />} />
          </Routes>
        </ModalProvider>
      </BrowserRouter>
    </>
  );
}

export default App;
