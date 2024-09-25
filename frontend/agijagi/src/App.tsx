import { BrowserRouter, Route, Routes } from 'react-router-dom';

import HomePage from './pages/HomePage';
import WritingDiary from './pages/WritingDiary';
import ActivityRecordPage from './pages/ActivityRecordPage';
import SchedulePage from './pages/SchedulePage';
import BabyMain from './pages/BabyMain';
import Login from './pages/Login';

import GlobalStyle from './styles/GlobalStyle';

import { ModalProvider } from './hooks/useModal';
import BoardPage from './pages/BoardPage';

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
            <Route path="/baby/writing" element={<WritingDiary />} />
            <Route path="/login" element={<Login />} />
            <Route path="/schedule" element={<SchedulePage />} />
            <Route path="/board" element={<BoardPage />} />
          </Routes>
        </ModalProvider>
      </BrowserRouter>
    </>
  );
}

export default App;
