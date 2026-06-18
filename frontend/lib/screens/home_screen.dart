import 'package:flutter/material.dart';
import '../services/api_service.dart';

class HomeScreen extends StatefulWidget {
  const HomeScreen({super.key});

  @override
  State<HomeScreen> createState() => _HomeScreenState();
}

class _HomeScreenState extends State<HomeScreen> {
  final ApiService _apiService = ApiService();
  final List<String> sectors = ['SECTOR_A', 'SECTOR_B', 'SECTOR_C'];

  List<dynamic> reservations = [];
  bool isLoading = true;

  @override
  void initState() {
    super.initState();
    _fetchReservations();
  }

  Future<void> _fetchReservations() async {
    setState(() => isLoading = true);
    try {
      final data = await _apiService.getReservations();
      setState(() {
        reservations = data;
        isLoading = false;
      });
    } catch (e) {
      setState(() => isLoading = false);
      _showSnackBar('Server connection error: $e', Colors.red);
    }
  }

  Future<void> _makeReservation(String sector) async {
    final now = DateTime.now();
    final startTime = now.toIso8601String();
    final endTime = now.add(const Duration(hours: 1)).toIso8601String();

    Navigator.pop(context);

    try {
      await _apiService.createReservation(sector, startTime, endTime);
      _showSnackBar('Reservation successful!', Colors.green);
      _fetchReservations();
    } catch (e) {
      _showSnackBar(e.toString().replaceAll('Exception: ', ''), Colors.red);
    }
  }

  Future<void> _deleteReservation(int id) async {
    try {
      await _apiService.deleteReservation(id);
      _showSnackBar('Reservation deleted', Colors.orange);
      _fetchReservations();
    } catch (e) {
      _showSnackBar('Error during deletion: $e', Colors.red);
    }
  }

  void _showSnackBar(String message, Color color) {
    ScaffoldMessenger.of(context).showSnackBar(
      SnackBar(content: Text(message), backgroundColor: color),
    );
  }

  void _showBookingBottomSheet(String sector) {
    showModalBottomSheet(
      context: context,
      shape: const RoundedRectangleBorder(
        borderRadius: BorderRadius.vertical(top: Radius.circular(20)),
      ),
      builder: (context) {
        return Padding(
          padding: const EdgeInsets.all(24.0),
          child: Column(
            mainAxisSize: MainAxisSize.min,
            crossAxisAlignment: CrossAxisAlignment.stretch,
            children: [
              Text(
                'Book: ${sector.replaceAll('_', ' ')}',
                style: const TextStyle(fontSize: 22, fontWeight: FontWeight.bold),
                textAlign: TextAlign.center,
              ),
              const SizedBox(height: 24),
              OutlinedButton.icon(
                icon: const Icon(Icons.calendar_today),
                label: const Text('Book next hour'),
                style: OutlinedButton.styleFrom(
                  padding: const EdgeInsets.symmetric(vertical: 16),
                ),
                onPressed: () => _makeReservation(sector),
              ),
            ],
          ),
        );
      },
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Arena Booker', style: TextStyle(fontWeight: FontWeight.bold)),
        elevation: 0,
        backgroundColor: Colors.transparent,
        foregroundColor: Colors.black87,
        actions: [
          IconButton(
            icon: const Icon(Icons.logout, size: 28),
            onPressed: () async {
              await _apiService.logout();
              // TODO: Navigate to login screen
            },
          )
        ],
      ),
      body: Padding(
        padding: const EdgeInsets.symmetric(horizontal: 16.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            const Text('Quick Booking', style: TextStyle(fontSize: 20, fontWeight: FontWeight.bold)),
            const SizedBox(height: 16),
            SizedBox(
              height: 140,
              child: ListView.builder(
                scrollDirection: Axis.horizontal,
                itemCount: sectors.length,
                itemBuilder: (context, index) {
                  return GestureDetector(
                    onTap: () => _showBookingBottomSheet(sectors[index]),
                    child: Card(
                      margin: const EdgeInsets.only(right: 16),
                      shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(20)),
                      child: Container(
                        width: 140,
                        decoration: BoxDecoration(
                          borderRadius: BorderRadius.circular(20),
                          gradient: const LinearGradient(
                            colors: [Colors.blueAccent, Colors.lightBlue],
                          ),
                        ),
                        child: Column(
                          mainAxisAlignment: MainAxisAlignment.center,
                          children: [
                            const Icon(Icons.sports_volleyball, color: Colors.white, size: 45),
                            const SizedBox(height: 12),
                            Text(
                              sectors[index].replaceAll('_', ' '),
                              style: const TextStyle(color: Colors.white, fontWeight: FontWeight.bold),
                            ),
                          ],
                        ),
                      ),
                    ),
                  );
                },
              ),
            ),
            const SizedBox(height: 40),
            const Text('Your Reservations', style: TextStyle(fontSize: 20, fontWeight: FontWeight.bold)),
            const SizedBox(height: 16),
            Expanded(
              child: isLoading
                  ? const Center(child: CircularProgressIndicator())
                  : reservations.isEmpty
                  ? const Center(
                child: Text(
                  'You have no upcoming trainings.',
                  style: TextStyle(color: Colors.grey, fontSize: 16),
                ),
              )
                  : ListView.builder(
                itemCount: reservations.length,
                itemBuilder: (context, index) {
                  final res = reservations[index];

                  final DateTime startTime = DateTime.parse(res['startTime']);
                  final String displayTime = "${startTime.hour.toString().padLeft(2, '0')}:${startTime.minute.toString().padLeft(2, '0')}";

                  return Card(
                    margin: const EdgeInsets.only(bottom: 12),
                    shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(15)),
                    child: ListTile(
                      leading: const CircleAvatar(
                        backgroundColor: Colors.blueAccent,
                        child: Icon(Icons.check, color: Colors.white),
                      ),
                      title: Text(
                        res['sector'].replaceAll('_', ' '),
                        style: const TextStyle(fontWeight: FontWeight.bold),
                      ),
                      subtitle: Text('Starts at: $displayTime'),
                      trailing: IconButton(
                        icon: const Icon(Icons.delete_outline, color: Colors.redAccent),
                        onPressed: () => _deleteReservation(res['id']),
                      ),
                    ),
                  );
                },
              ),
            ),
          ],
        ),
      ),
    );
  }
}