//
//  CalendarPickerViewController.swift
//  Cherrybnb
//
//  Created by Bumgeun Song on 2022/05/27.
//  Copyright © 2022 Codesquad. All rights reserved.
//

import UIKit

typealias DateSelection = (checkIn: Date, checkOut: Date?)

class CalendarPickerViewController: UIViewController {

    static let defaultNumberOfMonths = 12
    
    lazy var collectionView: UICollectionView = {
        let layout = UICollectionViewFlowLayout()
        layout.headerReferenceSize = CGSize(width: 400, height: 50)
        layout.minimumLineSpacing = 0
        layout.minimumInteritemSpacing = 0

        let collectionView = UICollectionView(frame: .zero, collectionViewLayout: layout)
        collectionView.isScrollEnabled = false
        collectionView.translatesAutoresizingMaskIntoConstraints = false

        collectionView.register(CalendarPickerViewCell.self, forCellWithReuseIdentifier: CalendarPickerViewCell.reuseIdentifier)
        collectionView.register(CalendarPickerViewSectionHeader.self, forSupplementaryViewOfKind: UICollectionView.elementKindSectionHeader, withReuseIdentifier: CalendarPickerViewSectionHeader.reuseIdentifier)

        collectionView.delegate = self
        collectionView.dataSource = self

        collectionView.isScrollEnabled = true
        return collectionView
    }()

    var calendarPicker: CalendarPicker
    
    init(baseDate: Date, numOfMonths: Int,
         didSelectDate: ((DateSelection) -> Void)? = nil) {
        self.calendarPicker = CalendarPicker(baseDate: baseDate, numOfMonths: numOfMonths, didSelectDate: didSelectDate)

        super.init(nibName: nil, bundle: nil)
    }
    
    func setSelectionHandler(_ didSelectDate: ((DateSelection) -> Void)?) {
        self.calendarPicker.didSelectDate = didSelectDate
    }

    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }

    override func viewDidLoad() {
        super.viewDidLoad()

        setSubviews()
        setLayout()
    }

    private func setSubviews() {
        view.addSubview(collectionView)
    }

    private func setLayout() {
        NSLayoutConstraint.activate([
            collectionView.leadingAnchor.constraint(equalTo: view.readableContentGuide.leadingAnchor),
            collectionView.trailingAnchor.constraint(equalTo: view.readableContentGuide.trailingAnchor),
            collectionView.topAnchor.constraint(equalTo: view.readableContentGuide.topAnchor),
            collectionView.bottomAnchor.constraint(equalTo: view.readableContentGuide.bottomAnchor)
        ])
    }
}

extension CalendarPickerViewController: UICollectionViewDataSource {
    func numberOfSections(in collectionView: UICollectionView) -> Int {
        return calendarPicker.monthCount
    }

    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return calendarPicker.dayCount(monthSection: section)
    }

    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        guard let cell = collectionView.dequeueReusableCell(withReuseIdentifier: CalendarPickerViewCell.reuseIdentifier, for: indexPath) as? CalendarPickerViewCell else {
            return UICollectionViewCell()
        }

        let day = calendarPicker.getDay(monthSection: indexPath.section, dayItem: indexPath.item)

        cell.setDay(day)

        return cell
    }

    func collectionView(_ collectionView: UICollectionView, viewForSupplementaryElementOfKind kind: String, at indexPath: IndexPath) -> UICollectionReusableView {
        switch kind {
        case UICollectionView.elementKindSectionHeader:
            guard let headerView = collectionView.dequeueReusableSupplementaryView(
                ofKind: kind,
                withReuseIdentifier: CalendarPickerViewSectionHeader.reuseIdentifier,
                for: indexPath) as? CalendarPickerViewSectionHeader else {
                return UICollectionReusableView()
            }
            headerView.setMonth(calendarPicker.getMonth(monthSection: indexPath.section))
            return headerView

        default: return UICollectionReusableView()
        }
    }

}

extension CalendarPickerViewController: UICollectionViewDelegate {
    // TODO: HANDLING SELECTION
}
