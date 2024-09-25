import { BrowserRouter, Route, Routes } from 'react-router-dom';
import BookCarousel from './components/book/BookCarousel';
import Home from './pages/Home';
import WritingDiary from './pages/WritingDiary';
import ActivityRecord from './pages/ActivityRecord';
import SchedulePage from './pages/Schedule';
import GlobalStyle from './styles/GlobalStyle';
import { ModalProvider } from './hooks/useModal';
import MileStoneCheck from './components/milestone/MileStone';
import BabyMain from './pages/BabyMain';
import MileStoneReport from './components/milestone/Report';

function App() {
  return (
    <>
      <GlobalStyle />
      <ModalProvider>
        <BrowserRouter>
          <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/book" element={<BookCarousel />} />
            <Route path="/baby/writing" element={<WritingDiary />} />
            <Route path="/activity-record" element={<ActivityRecord />} />
            <Route path="/milestone" element={<MileStoneCheck />} />
            <Route path="/baby" element={<BabyMain />} />
            <Route path="/schedule" element={<SchedulePage />} />
            <Route path="/milestone/report" element={<MileStoneReport />} />
          </Routes>
        </BrowserRouter>
      </ModalProvider>
    </>
  );
}

export default App;
