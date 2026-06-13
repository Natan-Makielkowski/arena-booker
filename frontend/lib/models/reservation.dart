import 'sector.dart';

class Reservation {
  final String id;
  final Sector sector;
  final DateTime startTime;
  final DateTime endTime;

  Reservation({
    required this.id,
    required this.sector,
    required this.startTime,
    required this.endTime,
  });

  static Reservation fromJson(Map<String, dynamic> json) {
    return Reservation(
      id: json['id'].toString(),
      sector: Sector.values.firstWhere(
        (e) => e.name == json['sector'],
        orElse: () => Sector.sectorA,
      ),
      startTime: DateTime.parse(json['startTime']),
      endTime: DateTime.parse(json['endTime']),
    );
  }
}